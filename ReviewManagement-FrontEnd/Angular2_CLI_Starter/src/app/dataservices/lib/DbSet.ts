import {IDbSet, IDbContext, Expression, IDbEntry, DbEntryState, IPredict} from './interfaces';
import {metadatakeys} from './annotations';
import 'reflect-metadata';

class DbSet < TEntity extends Object > implements IDbSet < TEntity > {
    private _nevigationProperties: Array<string> = [];
    private _innerEntityObj: TEntity;
    private _innerNevigationProperties: {[name: string]: Array<string>} = {};
    private _name: string;
    constructor(private _context: IDbContext, private _entityType: {new (): TEntity}) {
        this._innerEntityObj = new this._entityType();
        this._name = this._entityType.name;
        if (!this.isEntity(this._entityType)) {
            throw new Error('This is not Entity');
        }
    }

    private get Context(): IDbContext{
        return this._context;
    }

    private isEntity(target: any): boolean {
        let isEnt = Reflect.getOwnMetadata(metadatakeys.Entry, target) || false;
        return isEnt;
    }

    include(navigationProperty: Expression < TEntity, string > ): void {

        let nav = navigationProperty(new this._entityType());
        if (!this._innerEntityObj[nav]) {
            return;
        }
        this._nevigationProperties.push(nav);
    }

    private loadNavigation(entityPromis: TEntity, name: string): Promise<TEntity> {
        if (this._innerNevigationProperties[name].length <= 0) {
              return Promise.resolve(entityPromis);
        }
        let nav = this._innerNevigationProperties[name].pop();
        let tp = Reflect.getMetadata('design:type', entityPromis, nav);
        let destTypeName: string = tp.name;

        let s: string[] = Reflect.getMetadata(metadatakeys.ForeignKey, entityPromis, nav) || [];
        if (s.length === 0) {
           return Promise.reject<TEntity>('Navigation not fount for ${nav}');
        }

        let values = [];

        s.forEach(element => {
            values.push(entityPromis[element]);
        });

        return this.Context.find(values, destTypeName).then((val) => {
            entityPromis[nav] = val;
            return this.loadNavigation(entityPromis, name);
        });
    }

    find(id: any[]): Promise < TEntity >  {
        return this.Context.find<TEntity>(id, this._entityType).then(entity => {
            let name = Math.random().toString();
            this._innerNevigationProperties[name] = this._nevigationProperties;
            return this.loadNavigation(entity, name).then( ent => {
                try {
                    delete this._innerNevigationProperties[name];
                }catch (s) {

                }
                return ent;
            });
        });
    }

    add(entity: TEntity): void {
        let entry: IDbEntry = this.Context.createEntry(entity);
        entry.state = DbEntryState.New;
        entity['__Entry'] = entry;
        this.Context.Entries[this._entityType.name].push(entry);
    }

    remove(entity: TEntity): void {
        let entry: IDbEntry = entity['__Entry'];
        entry.state = DbEntryState.Deleted;
    }

    attach(entity: TEntity): void {
        let entry: IDbEntry = entity['__Entry'];
        entry.state = DbEntryState.Modified;
    }

    toList(): Promise < Array < TEntity >>  {
        return this.Context.toList<TEntity>(this._name).then((list) => {
            return new Promise((resolve, reject) => {
                let cnts = new Array<number>();
                let count = list.length;
                if (!list) {
                    resolve(null);
                }
                list.forEach(element => {
                    let name = '_sss' + (Math.floor(Math.random() * 100000) + 1  ).toString();
                    this._innerNevigationProperties[name] = this._nevigationProperties;
                    this.loadNavigation(element, name).then(s => {
                        cnts.push(1);
                        try {
                            delete this._innerNevigationProperties[name];
                        }catch (s) {

                        }
                    });
                });
                let s = function(){
                    if (cnts.length !== count) {
                        setTimeout(s, 500);
                    }else {
                        resolve(list);
                    }
                };
                s();
            });
        });
    }

    orderBy < String > (order: Expression < TEntity, string | string[] > ): IDbSet < TEntity >  {
        this.Context.orderBy(this._name, order(this._innerEntityObj));
        return this;
    }

    skip(count: number): IDbSet < TEntity >  {
        this.Context.skip(this._name, count);
        return this;
    }

    take(count: number): IDbSet < TEntity >  {
        this.Context.take(this._name, count);
        return this;
    }

    default(defaul: TEntity): IDbSet < TEntity >  {
        this.Context.default(this._name, defaul);
        return this;
    }

    firstOrDefault(predict: Expression < TEntity, boolean | IPredict[] > ): Promise < TEntity >  {
        this.where(predict);
        return this._context.firstOrDefault<TEntity>(this._name, this._entityType).then(entity => {
            let name = Math.random().toString();
            this._innerNevigationProperties[name] = this._nevigationProperties;
            return this.loadNavigation(entity, this._name).then( ent => {
                try {
                    delete this._innerNevigationProperties[name];
                }catch (s) {

                }
                return ent;
            });
        });
    }

    first(predict: Expression < TEntity, boolean | IPredict[] > ): Promise < TEntity >  {
        this.where(predict);
        return this._context.first<TEntity>(this._name, this._entityType).then(entity => {
            let name = Math.random().toString();
            this._innerNevigationProperties[name] = this._nevigationProperties;
            return this.loadNavigation(entity, this._name).then( ent => {
                try {
                    delete this._innerNevigationProperties[name];
                }catch (s) {

                }
                return ent;
            });
        });
    }

    lastOrDefault(predict: Expression < TEntity, boolean | IPredict[] > ): Promise < TEntity >  {
        this.where(predict);
        return this._context.lastOrDefault(this._name, this._entityType).then(entity => {
            let name = Math.random().toString();
            this._innerNevigationProperties[name] = this._nevigationProperties;
            return this.loadNavigation(entity, this._name).then( ent => {
                try {
                    delete this._innerNevigationProperties[name];
                }catch (s) {

                }
                return ent;
            });
        });
    }

    last(predict: Expression < TEntity, boolean | IPredict[] > ): Promise < TEntity >  {
        this.where(predict);
        return this._context.last(this._name, this._entityType).then(entity => {
            let name = Math.random().toString();
            this._innerNevigationProperties[name] = this._nevigationProperties;
            return this.loadNavigation(entity, this._name).then( ent => {
                try {
                    delete this._innerNevigationProperties[name];
                }catch (s) {

                }
                return ent;
            });
        });
    }

    where(predict: Expression < TEntity, boolean | IPredict[] > ): IDbSet < TEntity >  {
        this._context.where(this._name, predict);
        return this;
    }
}

export {DbSet};
