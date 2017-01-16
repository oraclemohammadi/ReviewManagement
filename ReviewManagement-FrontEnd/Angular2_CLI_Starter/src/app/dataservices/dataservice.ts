import { Injectable} from '@angular/core';
import {IGenericRepository, IDbContextTransaction} from './lib/interfaces';
import {DataProvider} from './dataprovider';
import { DbContext } from './lib/DbContext';
import {DbSet} from './lib/Dbset';
import {GenericRepository} from './lib/genericRepository';
import { Entities} from './classes';
import { UserDto } from '../dto/User.DTO';
import {MappingUtility} from './lib/mapping.utility';


class SalesCompetitorAnalysisDbContext extends DbContext {
    constructor() {
        let restProv = new DataProvider();
        super([restProv], Entities, MappingUtility.getKeys);
        this.ActiveProvider = restProv;
        this.createDatasets();
    }

    private createDatasets(): void {
        this.DbSet = {
            name: 'UserDto',
            dbSet: new DbSet<UserDto>(this, UserDto)
        };
    }
}

@Injectable()
class DataService {
    private _context: SalesCompetitorAnalysisDbContext;
    private _trans: IDbContextTransaction;

    constructor() {
        this._context = new SalesCompetitorAnalysisDbContext();
    }

    save(): Promise<number> {
        return this._context.saveChanges();
    }

    getRepository<T>(entityType: {new (): T}): IGenericRepository<T> {
        return new GenericRepository<T>(this._context, entityType);
    }

    beginTransaction(): Promise<boolean> {
        return new Promise<boolean>((resolve, reject) => {
            if (this._trans) {
                resolve(true);
                return;
            }
            this._context.beginTransaction().then(trn => {
                this._trans = trn;
                resolve(true);
            }).catch(res => {
                reject(res);
            });
        });
    }

    commitTransaction():  void {
        if (!this._trans) {
            return;
        }
        this._trans.commit();
        this._trans = null;
    }

    rollbackTransaction(): void {
        if (!this._trans) {
            return ;
        }
        this._trans.rollback();
        this._trans = null;
    }
}

export { DataService };
