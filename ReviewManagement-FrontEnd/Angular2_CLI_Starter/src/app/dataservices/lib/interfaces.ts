interface IDbContext {
    ActiveProvider: IDbProvider;
    Entries: IDbEntry[];
    createEntry(entity: any): IDbEntry;
    set<TEntity>(name: string): IDbSet<TEntity>;
    entry(entry: Object): IDbEntry;
    saveChanges(name: string): Promise<number>;
    beginTransaction(): Promise<IDbContextTransaction>;
    find<TEntity>(id: any[], type: {new (): TEntity} | string): Promise<TEntity>;
    toList<TEntity>(type: {new (): TEntity} | string): Promise < Array < TEntity >>;
    orderBy (dbset: string, order: string | string[] );
    skip(dbset: string, count: number);
    take(dbset: string, count: number);
    default<TEntity>(dbset: string, defaul: TEntity);
    firstOrDefault<TEntity>(dbset: string, type: {new (): TEntity} | string): Promise < TEntity >;
    first<TEntity>(dbset: string, type: {new (): TEntity} | string): Promise < TEntity >;
    lastOrDefault<TEntity>(dbset: string, type: {new (): TEntity} | string): Promise < TEntity >;
    last<TEntity>(dbset: string, type: {new (): TEntity} | string): Promise < TEntity >;
    where<TEntity>(dbset: string, predict: Expression < TEntity, boolean | IPredict[] >);
}

interface IDbProvider {
    name: string;
    beginTransaction(): Promise<IDbContextTransaction>;
    query<TEntity>(query: IDbQueryExpression): Promise<Array<TEntity>>;
    noResultQuery(query: IDbQueryExpression): Promise<number>;
    createUpdateQuery<TEntity>(entity: TEntity): IDbQueryExpression;
    createDeleteQuery<TEntity>(entity: TEntity): IDbQueryExpression;
    createInsertQuery<TEntity>(entity: TEntity): IDbQueryExpression;
    createSelectQuery<TEntity>(entityType: {new(): TEntity},
                           predictFunc?: Expression<TEntity, boolean> ,
                           predict?: IPredict[],
                           joins?: {
                                      type: 'INNER'|'RIGHT'|'LEFT'|'FULL',
                                      on: {join: 'OR' | 'AND' | '', operation: string, key: string, value: any}[]
                                  }[],
                           orderby?: string[], take?: number, skip?: number): IDbQueryExpression;
}

interface IDbSet<TEntity> {
    include(navigationProperty: Expression<TEntity, Object>): void;
    find(id: any[]): Promise<TEntity>;
    add(entity: TEntity): void;
    remove(entity: TEntity): void;
    attach(entity: TEntity): void;
    toList(): Promise<Array<TEntity>>;
    orderBy<TKey>(order: Expression<TEntity, TKey>): IDbSet<TEntity>;
    skip(count: number): IDbSet<TEntity>;
    take(count: number): IDbSet<TEntity>;
    default(defaul: TEntity): IDbSet<TEntity>;
    firstOrDefault(predict: Expression < TEntity, boolean | IPredict[] >): Promise<TEntity>;
    first(predict: Expression < TEntity, boolean | IPredict[] >): Promise<TEntity>;
    lastOrDefault(predict: Expression < TEntity, boolean | IPredict[] >): Promise<TEntity>;
    last(predict: Expression < TEntity, boolean | IPredict[] >): Promise<TEntity>;
    where(predict: Expression < TEntity, boolean | IPredict[] >): IDbSet<TEntity>;
}

interface IDbEntry {
    state: DbEntryState;
    wraped: any;
}

enum DbEntryState {
    New,
    Modified,
    Deleted,
    None
}

interface IGenericRepository<TEntity> {
    getAll(... params: Expression<TEntity, Object>[]): Promise<Array<TEntity>>;
    getAllBetween<Tkey>(orderBy: Expression<TEntity, Tkey>, skip: number, take: number,
                               ... params: Expression<TEntity, Object>[]): Promise<Array<TEntity>>;
    getById(id: any, ... params: Expression<TEntity, Object>[]): Promise<TEntity>;
    getSingle(predict: Expression < TEntity, boolean | IPredict[] >
              , ... params: Expression<TEntity, Object>[]): Promise<TEntity>;
    getList(predict: Expression < TEntity, boolean | IPredict[] >
              , ... params: Expression<TEntity, Object>[]): Promise<Array<TEntity>>;
    getListBetween<Tkey>(predict: Expression < TEntity, boolean | IPredict[] >, orderBy: Expression<TEntity, Tkey>,
                          skip: number, take: number, ... params: Expression<TEntity, Object>[]): Promise<Array<TEntity>>;
    any(id: any[], ... params: Expression<TEntity, Object>[]): Promise<boolean>;
    insert(entity: TEntity): void;
    delete(entity: TEntity | any): void;
    update(entity: TEntity): void;
}

interface Expression<TIn, TOut>{
    (input: TIn): TOut;
}

interface IDbContextTransaction {
    commit(): void;
    rollback(): void;
}

interface IDbQueryExpression {
    tableName: string;
    entityType?: {new(): any };
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

interface IPredict {
    join: 'OR' | 'AND' | '';
    operation: string;
    key: string;
    value: any;
}

export {IDbContext,
     IDbSet,
     IDbEntry,
     DbEntryState,
     IGenericRepository,
     Expression,
     IDbContextTransaction,
     IDbProvider,
     IPredict,
     IDbQueryExpression }
