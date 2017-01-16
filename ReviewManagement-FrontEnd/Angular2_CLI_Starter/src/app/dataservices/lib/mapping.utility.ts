import 'reflect-metadata';
import {metadatakeys, ITableMetaData} from './annotations';

class MappingUtility {
   static getMapping<TEntity>(entity: TEntity): {[jsonkey: string]: string} {
       let entObj: Object = entity;
       let dic: {[jsonkey: string]: string} = {};
       Object.getOwnPropertyNames(entity).forEach(function(key, idx, array) {
            if (typeof(entity[key]) !== 'function') {
               let getNotMapped = Reflect.getOwnMetadata(metadatakeys.NotMapped, entity, key) || false;
               if (!getNotMapped) {
                    let innerColumn: {key: string; name: string} = Reflect.getOwnMetadata(metadatakeys.InnerColumn, entity, key );
                    if ( !innerColumn) {
                        let colName: string = key.startsWith('_') ? key.substr(1) : key;
                        dic[colName] = key;
                    }else {
                        dic[innerColumn.name] = innerColumn.key;
                    }
               }
           }
        });
    //    for (let key in entity) {
    //        if (entObj.hasOwnProperty(key) && typeof(entity[key]) !== 'function') {
    //            let getNotMapped = Reflect.getOwnMetadata(metadatakeys.NotMapped, entity, key) || false;
    //            if (getNotMapped) {
    //                continue;
    //            }
    //            let innerColumn: {key: string; name: string} = Reflect.getOwnMetadata(metadatakeys.InnerColumn, entity, key );
    //            if ( !innerColumn) {
    //                let colName: string = key.startsWith('_') ? key.substr(1) : key;
    //                dic[colName] = key;
    //            }else {
    //                dic[innerColumn.name] = innerColumn.key;
    //            }
    //        }
    //    }
       return dic;
   }

   static  getTableName<TEntity>(entity: TEntity): string {
       let tableName: ITableMetaData = Reflect.getOwnMetadata(metadatakeys.Table, entity);
       if (tableName) {
           return tableName.name;
       }
       return null;
   }

   static getKeys<TEntity>(type: {new (): TEntity}): Array<string> {
       let obj = new type();
       let keys: any[] = obj['__Id__'];
       return keys;
   }
}

export { MappingUtility } ;
