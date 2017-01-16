import { IDbProvider, IDbContextTransaction, IDbQueryExpression, Expression, IPredict } from '../interfaces';

class RestServiceProvider implements IDbProvider {
    protected services: {[name: string]: {
        getAll?: () => Promise<Array<any>>;
        getEntity?: () => Promise<any>;
        update?: <TEntity>(entity: TEntity) => Promise<boolean>;
        delete?: <TEntity>(entity: TEntity) => Promise<boolean>;
        insert?: <TEntity>(entity: TEntity) => Promise<boolean>;
    }};

    constructor(private _name: string, private _mapper: {<TEntity>(entity: TEntity): {[jsonkey: string]: string}}
                , private _tableName: {<TEntity>(entity: TEntity): string}) {
    }

    get name(): string {
        return this._name;
    }

    beginTransaction(): Promise<IDbContextTransaction> {
        return Promise.resolve(new DbTransaction());
    }

    protected getMapping<TEntity>(entity: TEntity): {[jsonkey: string]: string} {
        return this._mapper(entity);
    }

    private mapJsonToEntity<TEntity>(json: any, entityType: {new (): TEntity}): TEntity {
        let entity = new entityType();

        let maps = this.getMapping(entity);

        for (let jsonKey in maps) {
            if (jsonKey) {
                entity[maps[jsonKey]] = json[jsonKey];
            }
        }
        return entity;
    }

    private mapEntityToJson<TEntity>(entity: TEntity): any {
        let json = Object.create({});

        let maps = this.getMapping(entity);

        for (let jsonKey in maps) {
            if (jsonKey) {
                json[jsonKey] = entity[maps[jsonKey]];
            }
        }
    }

    query<TEntity>(query: IDbQueryExpression): Promise<Array<TEntity>> {
        if (!this.services[query.entityType.name]) {
            return Promise.reject<Array<TEntity>>('Table not found');
        }
        let service = this.services[query.entityType.name];
        if (query.select && !service.getAll) {
           return Promise.reject<Array<TEntity>>('Table not found');
        }

        return service.getAll().then(results => {
            let entities = [];
            results.forEach(val => {
                let entity = this.mapJsonToEntity(val, query.entityType);
                if (query.whereFunc) {
                    if (query.whereFunc(entity)) {
                        entities.push(entity);
                    }
                } else {
                    entities.push(entity);
                }
            });
            if (query.orderby) {
                // TODO:
            }
            if (query.skip || query.take) {
                let filtered = [];
                let skip = query.skip || 0;
                let take = query.take || 0;
                let count = 0;
                for (let i = 0; i < entities.length; i++) {
                    if (i + 1 > skip) {
                        if (take === 0 || count < take) {
                            filtered.push(entities[i]);
                        }
                    }
                }
            }
            return entities;
        });
    }

    noResultQuery(query: IDbQueryExpression): Promise<number> {
        if (!this.services[query.entityType.name]) {
            return Promise.reject<number>('Table not found');
        }
        let service = this.services[query.entityType.name];
        if (query.update && !service.update) {
           return Promise.reject<number>('Table not found');
        }

        if (query.delete && !service.delete) {
           return Promise.reject<number>('Table not found');
        }

        if (query.insert && !service.insert) {
            return Promise.reject<number>('Table not found');
        }

        let json = this.mapEntityToJson(query.entity);
        if (query.update) {
            return service.update(json).then(res => {
                return res ? 1 : 0;
            });
        }

        if (query.insert) {
            return service.insert(json).then(res => {
                return res ? 1 : 0;
            });
        }

        if (query.delete) {
            return service.delete(json).then(res => {
                return res ? 1 : 0;
            });
        }
    }

    private getTableName<TEntity>(entity: TEntity): string {
        return this._tableName(entity);
    }

    createUpdateQuery<TEntity>(entity: TEntity): IDbQueryExpression {
        let query: IDbQueryExpression = {
            tableName: this.getTableName(entity),
            // entityType: entityType,
            entity: entity,
            update: {}
        };
        return query;
    }

    createDeleteQuery<TEntity>(entity: TEntity): IDbQueryExpression {
        let query: IDbQueryExpression = {
            tableName: this.getTableName(entity),
            // entityType: entityType,
            entity: entity,
            delete: true
        };
        return query;
    }

    createInsertQuery<TEntity>(entity: TEntity): IDbQueryExpression {
        let query: IDbQueryExpression = {
            tableName: this.getTableName(entity),
            // entityType: entityType,
            entity: entity,
            insert: { keys: [], values: []}
        };
        return query;
    }
    createSelectQuery<TEntity>(entityType: {new(): TEntity},
                           predictFunc?: Expression<TEntity, boolean> ,
                           predict?: IPredict[],
                           joins?: {
                                      type: 'INNER'|'RIGHT'|'LEFT'|'FULL',
                                      on: {join: 'OR' | 'AND' | '', operation: string, key: string, value: any}[]
                                  }[],
                           orderby?: string[], take?: number, skip?: number): IDbQueryExpression  {
        let query: IDbQueryExpression = {
            tableName: this.getTableName(new entityType()),
            entityType: entityType,
            entity: new entityType,
            where: predict,
            whereFunc: predictFunc,
            join: joins,
            orderby: orderby,
            take: take,
            skip: skip
        };

        return query;
  }
}

class DbTransaction implements IDbContextTransaction {
    commit(): void {

    }
    rollback(): void {

    }
}

class DBQueryExpression implements IDbQueryExpression {
    tableName: string;
    entityType: {new(): any };
    entity?: any;
    select?: string[];
    where?: IPredict[];
    whereFunc?: Expression<any, boolean>;
    orderby?: string[];
    join?: {
        type: 'INNER'|'RIGHT'|'LEFT'|'FULL',
        on: {join: 'OR' | 'AND' | '', operation: string, key: string, value: any}[]
    }[];
    skip?: number;
    take?: number;
    update?: {[key: string]: any};
    insert?: {keys: string[]; values: any[] };
    delete?: boolean;
}

export { RestServiceProvider, DbTransaction, DBQueryExpression}
