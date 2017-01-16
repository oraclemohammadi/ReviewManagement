import {IGenericRepository, IDbContext, IDbSet, Expression, IPredict, DbEntryState} from './interfaces';
/**
 * The generic repository
 * 
 * @class GenericRepository
 * @implements {IGenericRepository<TEntity>}
 * @template TEntity
 */
class GenericRepository<TEntity> implements IGenericRepository<TEntity> {
    private readonly _context: IDbContext;
    private readonly _dbSet: IDbSet<TEntity>;

    /**
     *
     */
    constructor(context: IDbContext, Tentity: {new(): TEntity}) {
        this._context = context;
        this._dbSet = context.set<TEntity>(Tentity.name);
    }

    private includeNavigation(params: Expression<TEntity, string>[]): void {
        params.forEach(element => {
            this._dbSet.include(element);
        });
    }

    /**
     * Gets all objects in repository
     * 
     * @param {...Expression<TEntity, string>[]} params
     * @returns {Array<TEntity>}
     * 
     */
    public getAll(... params: Expression<TEntity, string>[]): Promise<Array<TEntity>> {
        this.includeNavigation(params);
        return this._dbSet.toList();
    }

    /**
     * Gets all objects in repository from skip to take
     * 
     * @template Tkey
     * @param {Expression<TEntity, Tkey>} orderBy
     * @param {number} skip
     * @param {number} take
     * @param {...Expression<TEntity, string>[]} params
     * @returns {Array<TEntity>}
     * 
     * @memberOf GenericRepository
     */
    public getAllBetween<Tkey>(orderBy: Expression<TEntity, Tkey>, skip: number, take: number,
                               ... params: Expression<TEntity, string>[]): Promise<Array<TEntity>> {
        this.includeNavigation(params);
        return this._dbSet.orderBy(orderBy).skip(skip).take(take).toList();
    }

    /**
     * Get the entity by ID.
     * 
     * @param {*} id
     * @param {...Expression<TEntity, string>[]} params
     * @returns {TEntity}
     * 
     * @memberOf GenericRepository
     */
    public getById(id: any[], ... params: Expression<TEntity, string>[]): Promise<TEntity> {
        this.includeNavigation(params);
        return this._dbSet.find(id);
    }

    /**
     * Get the Entity base on condition 
     * 
     * @param {Expression<TEntity,boolean>} predict
     * @param {...Expression<TEntity, string>[]} params
     * @returns {TEntity}
     * 
     * @memberOf GenericRepository
     */
    public getSingle(predict: Expression < TEntity, boolean | IPredict[] >, ... params: Expression<TEntity, string>[]): Promise<TEntity> {
        this.includeNavigation(params);
        return this._dbSet.firstOrDefault(predict);
    }

    /**
     * Gets the list of @type {TEntity} objects based on condition
     * 
     * @param {Expression<TEntity, boolean>} predict
     * @param {...Expression<TEntity, string>[]} params
     * @returns {IEnumerable<TEntity>}
     * 
     * @memberOf GenericRepository
     */
    public getList(predict: Expression < TEntity, boolean | IPredict[] >
                   , ... params: Expression<TEntity, string>[]): Promise<Array<TEntity>> {
        this.includeNavigation(params);
        return this._dbSet.where(predict).toList();
    }

    /**
     * Gets the list of @type {TEntity} objects based on condition
     * 
     * @template Tkey
     * @param {Expression<TEntity, boolean>} predict
     * @param {Expression<TEntity, Tkey>} orderBy
     * @param {number} skip
     * @param {number} take
     * @param {...Expression<TEntity, string>[]} params
     * @returns {IEnumerable<TEntity>}
     * 
     * @memberOf GenericRepository
     */
    public getListBetween<Tkey>(predict: Expression < TEntity, boolean | IPredict[] >, orderBy: Expression<TEntity, Tkey>,
                          skip: number, take: number, ... params: Expression<TEntity, string>[]): Promise<Array<TEntity>> {
        this.includeNavigation(params);
        return this._dbSet.orderBy(orderBy).where(predict).skip(skip).take(take).toList();
    }

    /**
     * Check if any entity with id exists
     * 
     * @param {*} id
     * @param {...Expression<TEntity, string>[]} params
     * @returns {boolean}
     * 
     * @memberOf GenericRepository
     */
    public any(id: any, ... params: Expression<TEntity, string>[]): Promise<boolean> {
        this.includeNavigation(params);
        return new Promise((resolve, reject) => {
            this._dbSet.find(id).then(e => {
                resolve(e != null);
            }).catch(e => {
                reject(e);
            });
        });
    }

    /**
     * Inserts entity
     * 
     * @param {TEntity} entity
     * 
     * @memberOf GenericRepository
     */
    public insert(entity: TEntity): void {
        this._dbSet.add(entity);
    }

    /**
     * Deletes entity form DataBase by it's Id
     * 
     * @param {(TEntity | any)} id
     * @returns {void}
     * 
     * @memberOf GenericRepository
     */
    public delete(id: TEntity | any): void {
        this._dbSet.find(id)
        .then(ent => {
            if (ent == null) {
                this._dbSet.remove(id);
            }else {
                this._dbSet.remove(ent);
            }
        }).catch(e => {
            this._dbSet.remove(id);
        });
    }

    public update(entity: TEntity): void {
        this._dbSet.attach(entity);
        this._context.entry(entity).state = DbEntryState.Modified;
    }
}

export {GenericRepository};


