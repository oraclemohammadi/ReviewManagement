import {IDbContext, IDbSet, IDbProvider, IDbEntry, IDbContextTransaction, Expression, DbEntryState, IPredict} from './interfaces';
import 'reflect-metadata';

class DbEntry implements IDbEntry {
    public state: DbEntryState;
    public wraped: any;
}

class DbContext implements IDbContext {
    private static _entries: IDbEntry[];

    private _activeProvider: IDbProvider;
    private _dbSets: {[name: string]: IDbSet<any>} = {};
    private _addedEntries: IDbEntry[] = [];
    private _removedEntries: IDbEntry[] = [];
    private _updatedEntries: IDbEntry[] = [];
    private _orderBys: {[name: string]: string[] } = {};
    private _skip: {[name: string]: number} = {};
    private _take: {[name: string]: number} = {};
    private _defaults: {[name: string]: any} = {};
    private _predictFunc: {[name: string]: Expression < any, boolean >} = {};
    private _predict: {[name: string]: IPredict[]} = {};
    protected entities: {[name: string]: {new (): any}} = {} ;
    constructor(private _providers: Array<IDbProvider>, entities: {[name: string]: {new (): any}},
    private _getKeys: {<TEntity>(type: {new (): TEntity} | string): Array<string> }) {
        DbContext._entries = [];
        this.entities = entities;
    }

    get Entries(): IDbEntry[] {
        return DbContext._entries;
    }

    set ActiveProvider(name: IDbProvider){
        this._providers.forEach(element => {
            if (element.name === name.name) {
                    this._activeProvider = name;
            }
        });
    }

    get ActiveProvider(): IDbProvider {
        return this._activeProvider;
    }

    createEntry(entity: any): IDbEntry {
        let entry = new DbEntry();
        entry.wraped = entity;
        entry.state = DbEntryState.None;
        return entry;
    }

    set<TEntity>(name: string): IDbSet<TEntity> {
        if (!this._dbSets[name]) {
            throw new Error('No db set found');
        }
        return this._dbSets[name];
    }

    set DbSet(options: {name: string; dbSet: IDbSet<any>; }) {
        if (this._dbSets[options.name]) {
            throw new Error('dbset already exists');
        }

        this._dbSets[options.name] = options.dbSet;
    }

    entry(entry: Object): IDbEntry {
        return entry['__Entry'];
    }

    saveChanges(): Promise<number> {
        let promis = new Promise<number>((resolve, reject) => {
            this.seperateChanges();
            this.addChanges().then(count => this.updateChanges(count))
            .then(count => this.removeChanges(count))
            .then(count => {
                let enteries = this.Entries;
                enteries.forEach(ent => {
                    ent.state = DbEntryState.None;
                });
                resolve(count);
            });
        });
        return this.beginTransaction().then(trans => promis);
    }

    private seperateChanges(): void {
        this.Entries.forEach(entry => {
                    if (entry.state === DbEntryState.New) {
                        let added = this._addedEntries || [];
                        added.push(entry);
                        this._addedEntries = added;
                    } else if (entry.state === DbEntryState.Modified) {
                        let modified = this._updatedEntries || [];
                        modified.push(entry);
                        this._updatedEntries = modified;
                    } else if (entry.state === DbEntryState.Deleted) {
                        let deleted = this._removedEntries || [];
                        deleted.push(entry);
                        this._removedEntries = deleted;
                    }
                });
    }

    private updateChanges(count?: number): Promise<number> {
        return this.updateEachChanges(count);
    }

    private addChanges(count?: number): Promise<number> {
        return this.addEachChanges(count);
    }

    private removeChanges(count?: number): Promise<number> {
        return this.removeEachChanges(count);
    }

    private updateEachChanges(count?: number): Promise<number> {
        let updateEnt: IDbEntry = this._updatedEntries.pop();
        // for (let key in this._updatedEntries) {
        //     if (!this._updatedEntries) {
        //         continue;
        //     }
        //     if (this._updatedEntries.length === 0) {
        //         delete this._updatedEntries[key];
        //         continue;
        //     }
        //     updateEnt = 
        //     break;
        // }
        if (updateEnt == null) {
            return Promise.resolve<number>(count);
        }
        return new Promise<number>((resolve, reject) => {
            let dbQuery = this._activeProvider.createUpdateQuery(updateEnt.wraped);
            this._activeProvider.noResultQuery(dbQuery).then(cnt => {
                updateEnt.state = DbEntryState.None;
                return this.updateEachChanges(cnt + count);
            });
        });
    }

    private addEachChanges(count?: number): Promise<number> {
        let addEnt: IDbEntry = this._addedEntries.pop();
        // for (let key in this._addedEntries) {
        //     if (!this._addedEntries[key]) {
        //         continue;
        //     }
        //     if (this._addedEntries[key].length === 0) {
        //         delete this._addedEntries[key];
        //         continue;
        //     }
        //     addEnt = 
        //     break;
        // }
        if ( addEnt == null) {
            return Promise.resolve<number>(count);
        }
        return new Promise<number>((resolve, reject) => {
            let dbQuery = this._activeProvider.createInsertQuery(addEnt.wraped);
            this._activeProvider.noResultQuery(dbQuery).then(cnt => {
                addEnt.state = DbEntryState.None;
                return this.addEachChanges(cnt + count);
            });
        });
    }

    private removeEachChanges(count?: number): Promise<number> {
        let remEnt: IDbEntry = this._removedEntries.pop();
        // for (let key in this._removedEntries) {
        //     if (!this._removedEntries[key]) {
        //         continue;
        //     }
        //     if (this._removedEntries[key].length === 0) {
        //         delete this._removedEntries[key];
        //         continue;
        //     }
        //     remEnt = 
        //     break;
        // }
        if ( remEnt == null) {
            return Promise.resolve<number>(count);
        }
        // let tp = this.entities[type];
        return new Promise<number>((resolve, reject) => {
            let dbQuery = this._activeProvider.createDeleteQuery(remEnt.wraped);
            this._activeProvider.noResultQuery(dbQuery).then(cnt => {
                remEnt.state = DbEntryState.None;
                return this.removeEachChanges(cnt + count);
            });
        });
    }

    beginTransaction(): Promise<IDbContextTransaction> {
        return this._activeProvider.beginTransaction();
    }

    find<TEntity>(id: any[], type: {new (): TEntity} | string): Promise < TEntity >  {
        if (id.length <= 0) {
            return Promise.reject<TEntity>('id is empty');
        }
        let keys = this.getKeys<TEntity>(type) || [];
        if (keys.length <= 0) {
            return Promise.reject<TEntity>('Entity has no key');
        }
        if (keys.length !== id.length) {
            return Promise.reject<TEntity>('Number of id values are less or more');
        }
        let nm = typeof type === 'string' ? type : type.name;
        let predicates = this._predict[nm] || [];

        for (let i = 0; i < keys.length; i++) {
            predicates.push({
                    join: i === 0 ?  '' : 'AND',
                    operation: '=',
                    key: keys[i],
                    value: id[i]
                });
        }

        this._predict[nm] = predicates;
        this._predictFunc[nm] = <TEntity>(entity: TEntity) => {
            return this.innerPredictFunc(nm, entity);
        };
        let tp = typeof(type) === 'string' ? this.entities[type] : type;
        let DbQuery = this._activeProvider.createSelectQuery(tp, this._predictFunc[nm], predicates);

        return this._activeProvider.query<TEntity>(DbQuery).then(res => {
            if (res === null || res.length <= 0) {
                this.clearCache(nm);
                return Promise.reject<TEntity>('Not found');
            }
            let entry = this.createEntry(res[0]);
            entry.state = DbEntryState.None;
             res[0]['__Entry'] = entry;
             let entries = this.Entries[nm] || [];
             entries.push(entry);
             this.Entries[nm] = entries;
             this.clearCache(nm);
             return res[0];
        });
    }

    private innerPredictFunc<TEntity>(name: string, entity: TEntity): boolean {
        // TODO:
        return false;
    }

    private getKeys<TEntity>(type: {new (): TEntity} | string): Array<string> {
        return this._getKeys(type);
    }

    orderBy (dbset: string, order: string | string [] ) {
        this._orderBys[dbset] = this._orderBys[dbset] || [];
        if (typeof order === 'string') {
            this._orderBys[dbset].push(order);
        } else {
            order.forEach(element => {
                this._orderBys[dbset].push(element);
            });
        }
    }

    skip(dbset: string, count: number) {
        this._skip[dbset] = count;
    }

    take(dbset: string, count: number) {
        this._take[dbset] = count;
    }

    default<TEntity>(dbset: string, defaul: TEntity) {
        this._defaults[dbset] = defaul;
    }

    toList<TEntity>(type: {new (): TEntity} | string): Promise < Array < TEntity >> {
        let nm = typeof type === 'string' ? type : type.name;
        let tp = typeof(type) === 'string' ? this.entities[type] : type;
        let DbQuery = this._activeProvider.createSelectQuery(tp, this._predictFunc[nm], this._predict[nm], null,
         this._orderBys[nm], this._take[nm], this._skip[nm]);
        return this._activeProvider.query<TEntity>(DbQuery).then(res => {
            this.clearCache(nm);
            let entries = this.Entries[nm] || [];
            res.forEach(rs => {
                let entry = this.createEntry(rs);
                entry.state = DbEntryState.None;
                rs['__Entry'] = entry;
                entries.push(entry);
            });
             this.Entries[nm] = entries;
             return res;
        });
    }

    firstOrDefault<TEntity>(dbset: string, type: {new (): TEntity} | string): Promise < TEntity > {
        let nm = typeof type === 'string' ? type : type.name;
        let tp = typeof(type) === 'string' ? this.entities[type] : type;
        let DbQuery = this._activeProvider.createSelectQuery(tp, this._predictFunc[nm], this._predict[nm], null,
        this._orderBys[nm], this._take[nm], this._skip[nm]);
        return this._activeProvider.query<TEntity>(DbQuery).then(res => {
             this.clearCache(nm);
             if (res === null || res.length <= 0) {
                return this._defaults[nm];
            }
            let entries = this.Entries[nm] || [];
            let entry = this.createEntry(res[0]);
            entry.state = DbEntryState.None;
            res[0]['__Entry'] = entry;
            entries.push(entry);
            this.Entries[nm] = entries;
            return res[0];
        });
    }

    first<TEntity>(dbset: string, type: {new (): TEntity} | string): Promise < TEntity > {
        let nm = typeof type === 'string' ? type : type.name;
        let tp = typeof(type) === 'string' ? this.entities[type] : type;
        let DbQuery = this._activeProvider.createSelectQuery(tp, this._predictFunc[nm], this._predict[nm], null,
        this._orderBys[nm], this._take[nm], this._skip[nm]);
        return this._activeProvider.query<TEntity>(DbQuery).then(res => {
             this.clearCache(nm);
             if (res === null || res.length <= 0) {
                return Promise.reject<TEntity>('Not found');
            }
            let entries = this.Entries[nm] || [];
            let entry = this.createEntry(res[0]);
            entry.state = DbEntryState.None;
            res[0]['__Entry'] = entry;
            entries.push(entry);
            this.Entries[nm] = entries;
            return res[0];
        });
    }

    lastOrDefault<TEntity>(dbset: string, type: {new (): TEntity} | string): Promise < TEntity > {
        let nm = typeof type === 'string' ? type : type.name;
        let tp = typeof(type) === 'string' ? this.entities[type] : type;
        let DbQuery = this._activeProvider.createSelectQuery(tp, this._predictFunc[nm], this._predict[nm], null,
        this._orderBys[nm], this._take[nm], this._skip[nm]);
        return this._activeProvider.query<TEntity>(DbQuery).then(res => {
             this.clearCache(nm);
             if (res === null || res.length <= 0) {
                return this._defaults[nm];
            }
            let entries = this.Entries[nm] || [];
            let entry = this.createEntry(res[res.length - 1 ]);
            entry.state = DbEntryState.None;
            res[res.length - 1 ]['__Entry'] = entry;
            entries.push(entry);
            this.Entries[nm] = entries;
            return res[res.length - 1];
        });
    }

    last<TEntity>(dbset: string, type: {new (): TEntity} | string): Promise < TEntity > {
        let nm = typeof type === 'string' ? type : type.name;
        let tp = typeof(type) === 'string' ? this.entities[type] : type;
        let DbQuery = this._activeProvider.createSelectQuery(tp, this._predictFunc[nm], this._predict[nm], null,
        this._orderBys[nm], this._take[nm], this._skip[nm]);
        return this._activeProvider.query<TEntity>(DbQuery).then(res => {
             this.clearCache(nm);
             if (res === null || res.length <= 0) {
                return Promise.reject<TEntity>('Not found');
            }
            let entries = this.Entries[nm] || [];
            let entry = this.createEntry(res[res.length - 1 ]);
            entry.state = DbEntryState.None;
            res[res.length - 1 ]['__Entry'] = entry;
            entries.push(entry);
            this.Entries[nm] = entries;
            return res[ res.length - 1 ];
        });
    }

    where<TEntity>(dbset: string, predict: Expression < TEntity, boolean | IPredict[] > ) {
        let s = predict(null);
        if (!s || typeof(s) === 'boolean') {
            this._predictFunc[dbset] = predict as Expression < TEntity, boolean >;
            return;
        }
        this._predict[dbset] = s;
    }

    private clearCache(name: string): void {
        delete this._orderBys[name];
        delete this._skip[name];
        delete this._take[name];
        delete this._defaults[name];
        delete this._predictFunc[name];
        delete this._predict[name];
    }
}

export {DbContext, DbEntry}
