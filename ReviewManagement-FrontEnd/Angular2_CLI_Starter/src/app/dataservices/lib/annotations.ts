/*
declare type ClassDecorator = 
      <TFunction extends Function>(target: TFunction) => TFunction | void;

declare type PropertyDecorator = 
      (target: Object, propertyKey: string | symbol) => void;

declare type MethodDecorator = 
      <T>(target: Object, propertyKey: string | symbol, descriptor: TypedPropertyDescriptor<T>) 
      => TypedPropertyDescriptor<T> | void;

declare type ParameterDecorator = 
      (target: Object, propertyKey: string | symbol, parameterIndex: number) => void;
*/

/*
let MethodDecoratorSample = function (target: Function, key: string, descriptor: PropertyDescriptor) {
        // save a reference to the original method
        // this way we keep the values currently in the 
        // descriptor and don't overwrite what another 
        // decorator might have done to the descriptor.
        let originalMethod = descriptor.value;

        // editing the descriptor/value parameter
        descriptor.value = function(... args: any[]) {
            // note usage of originalMethod here
            let result = originalMethod.apply(this, args);

            // Do Stuff

            return result;

        };

        // return edited descriptor as opposed to overwriting 
        // the descriptor by returning a new descriptor
        return descriptor;
    };

let PropertyDecoratorSample = function(target: any, key: string){
        // property Value
        let _val = this[key];

        // property getter
        let getter = function() {
            // Do staff
            return _val;
        };

        // property setter
        let setter = function(newValue){
            // Do staff
            _val = newValue;
        };

        // Delete PropertyDescriptor
        if ( delete this[key]) {
            // Create new property with getter and setter
            Object.defineProperty(target, key, {
                get: getter,
                set: setter,
                enumerable: true,
                configurable: true
            });
        }
    };

let ClassDecoratorSample = function(target: any){
        // save a reference to the original constructor
        let original = target;

        // a utility function to generate instances of a class
        function construct(constructor, args) {
            let c: any = function(){
                return constructor.apply(this, args);
            };

            c.prototype = constructor.prototype;
            return new c();
        };

        // the new constructor behaviour
        let f: any = function(... args ){
            // Do Stuff
            return construct(original, args);
        };

        // copy prototype so intanceof operator still works
        f.prototype = original.prototype;

        // return new constructor (will override original)
        return f;
    };

let ParameterDecoratorSample = function(target: any, key: string, index: number) {
    // save index
};


export function MethodDecoratorSampleFactory(... params: any[]) {
    // Do preperation stuff
    return MethodDecoratorSample;
}

export function PropertyDecoratorSampleFactory(... params: any[]) {
    // Do preperation stuff
    return PropertyDecoratorSample;
}

export function ClassDecoratorSampleFactory(... params: any[]) {
    // Do prepration

    return ClassDecoratorSample;
}

export function ParameterDecoratorSampleFactory(... params: any[]) {
    // Do prepration

    return ParameterDecoratorSample;
}

export function MultiTargetDecoratorSampleFactory(... params: any[]) {
    // Do prepration

    return function(... args: any[]) {
        switch (args.length) {
            case 1:
                return ClassDecoratorSample.apply(this, args);
            case 2:
                return PropertyDecoratorSample.apply(this, args);
            case 3:
                return typeof args[2] === 'number' ? ParameterDecoratorSample.apply(this, args) :
                                                     MethodDecoratorSample.apply(this, args);
            default:
                throw new Error('Decorators are not valid here!');
        }
    };
} */

// Reflection
/*

// define metadata on an object or property
Reflect.defineMetadata(metadataKey, metadataValue, target);
Reflect.defineMetadata(metadataKey, metadataValue, target, propertyKey);

// check for presence of a metadata key on the prototype chain of an object or property
let result = Reflect.hasMetadata(metadataKey, target);
let result = Reflect.hasMetadata(metadataKey, target, propertyKey);

// check for presence of an own metadata key of an object or property
let result = Reflect.hasOwnMetadata(metadataKey, target);
let result = Reflect.hasOwnMetadata(metadataKey, target, propertyKey);

// get metadata value of a metadata key on the prototype chain of an object or property
let result = Reflect.getMetadata(metadataKey, target);
let result = Reflect.getMetadata(metadataKey, target, propertyKey);

// get metadata value of an own metadata key of an object or property
let result = Reflect.getOwnMetadata(metadataKey, target);
let result = Reflect.getOwnMetadata(metadataKey, target, propertyKey);

// get all metadata keys on the prototype chain of an object or property
let result = Reflect.getMetadataKeys(target);
let result = Reflect.getMetadataKeys(target, propertyKey);

// get all own metadata keys of an object or property
let result = Reflect.getOwnMetadataKeys(target);
let result = Reflect.getOwnMetadataKeys(target, propertyKey);

// delete metadata from an object or property
let result = Reflect.deleteMetadata(metadataKey, target);
let result = Reflect.deleteMetadata(metadataKey, target, propertyKey);

// apply metadata via a decorator to a constructor
@Reflect.metadata(metadataKey, metadataValue)
class C {
  // apply metadata via a decorator to a method (property)
  @Reflect.metadata(metadataKey, metadataValue)
  method() {
  }
}

// Design-time type annotations
function Type(type) { return Reflect.metadata("design:type", type); }
function ParamTypes(...types) { return Reflect.metadata("design:paramtypes", types); }
function ReturnType(type) { return Reflect.metadata("design:returntype", type); }

*/


import 'reflect-metadata';
import {DbEntryState, IDbEntry} from './interfaces';

let metadatakeys = {
    Entry : 'EntryMetaDataKey',
    Key : 'KeyMetaDataKey',
    Required: 'RequiredMetaDataKey',
    MinLength: 'MinLengthMetaDataKey',
    MaxLength: 'MaxLengthMetaDataKey',
    Table: 'TableMetaDataKey',
    Column: 'ColumnMetaDataKey',
    NotMapped: 'NotMappedMetaDataKey',
    InnerColumn: 'InnerColumnMetaDataKey',
    DatabaseGenerated: 'DatabaseGeneratedMetaDataKey',
    ForeignKey: 'ForeignKeyMetaDataKey'
};

interface ITableMetaData {
    name: string;
    schema?: string;
}

interface IColumnMetaData {
    key: string;
    name: string;
    hasChanged: boolean;
    oldValue: any;
}


let EntityDecoratorFactory = function(... args: any[]){
    return function(target: any){
        // save a reference to the original constructor
        let original = target;

        // a utility function to generate instances of a class
        function construct(constructor, sargs) {
            let c: any = function(){
                return constructor.apply(this, sargs);
            };

            c.prototype = constructor.prototype;
            return new c();
        };

        // the new constructor behaviour
        let f: any = function(... sargs ){
            // Do Stuff
            return construct(original, sargs);
        };

        // copy prototype so intanceof operator still works
        f.prototype = original.prototype;

        let entry: IDbEntry;
        target.prototype.__Entry = entry;
        // target.prototype.__changed = function(name: string, oldValue: any){
        //     target.prototype.__Entry.state = DbEntryState.Modified;
        //     let keys: IColumnMetaData[] = Reflect.getOwnMetadata(metadatakeys.Column, target) || [];
        //     keys.forEach(key => {
        //         if (key.name === name) {
        //             key.hasChanged = true;
        //             key.oldValue = oldValue;
        //         }
        //     });

        //     Reflect.defineMetadata(metadatakeys.Column, keys, target);
        // };

        let metadataKey = metadatakeys.Entry;

        Reflect.defineMetadata(metadataKey, true, target);

        // return new constructor (will override original)
        return target;
    };
};

let KeyDecoratorFactory = function(Order?: number){

    let applyMetaKeys = function(target: any, key: string){
        let keys: any[] = Reflect.getOwnMetadata(metadatakeys.Key, target) || [];
        if (Order) {
            if (keys[Order]) {
                throw new Error('Order already exists!');
            }
            keys[Order] = key;
        }
        keys.push(key);
        target.__Id__ = keys;
        Reflect.defineMetadata(metadatakeys.Key, keys, target);
    };

    return function(target: any, key: string){
        applyMetaKeys(target, key);

        // property Value
        let _val = target[key];

        // property getter
        let getter = function() {
            // Do staff
            return _val;
        };

        // property setter
        let setter = function(newValue){
            // Do staff
            _val = newValue;
        };

        // Delete PropertyDescriptor
        if ( delete target[key]) {
            // Create new property with getter and setter
            Object.defineProperty(target, key, {
                get: getter,
                set: setter,
                enumerable: true,
                configurable: true
            });
        }
    };
};

let RequiredDecoratorFactory = function(){
    let applyMetaKeys = function(target: any, key: string){
        Reflect.defineMetadata(metadatakeys.Required, true, target, key);
        // Reflect.ownKeys
    };
    return function(target: any, key: string){
        applyMetaKeys(target, key);
        // property Value
        let _val = target[key];

        // property getter
        let getter = function() {
            // Do staff
            return _val;
        };

        // property setter
        let setter = function(newValue){
            // Do staff
            if (!newValue) {
                throw new Error('Value can not be null!.');
            }
            _val = newValue;
        };

        // Delete PropertyDescriptor
        if ( delete target[key]) {
            // Create new property with getter and setter
            Object.defineProperty(target, key, {
                get: getter,
                set: setter,
                enumerable: true,
                configurable: true
            });
        }
    };
};

let MinLengthDecoratorFactory = function(length: number){
    let applyMetaKeys = function(target: any, key: string){
        Reflect.defineMetadata(metadatakeys.MinLength, true, target, key);
    };
    return function(target: any, key: string){
        applyMetaKeys(target, key);

        let t = Reflect.getMetadata('design:type', target, key);
        let isStringOrArray = t.name === 'String' || t.name === 'Array';

        // property Value
        let _val = target[key];

        // property getter
        let getter = function() {
            // Do staff
            return _val;
        };

        // property setter
        let setter = function(newValue){
            // Do staff
            if (isStringOrArray && newValue.length < length) {
                throw new Error('Minimum Length not accuired.');
            }
            _val = newValue;
        };

        // Delete PropertyDescriptor
        if ( delete target[key]) {
            // Create new property with getter and setter
            Object.defineProperty(target, key, {
                get: getter,
                set: setter,
                enumerable: true,
                configurable: true
            });
        }
    };
};

let MaxLengthDecoratorFactory = function(length: number){

    let applyMetaKeys = function(target: any, key: string){
        Reflect.defineMetadata(metadatakeys.MaxLength, true, target, key);
    };

    return function(target: any, key: string){
        applyMetaKeys(target, key);

        let t = Reflect.getMetadata('design:type', target, key);
        let isStringOrArray = t.name === 'String' || t.name === 'Array';

        // property Value
        let _val = target[key];

        // property getter
        let getter = function() {
            // Do staff
            return _val;
        };

        // property setter
        let setter = function(newValue){
            // Do staff
            if (isStringOrArray && newValue.length > length) {
                throw new Error('Maximum Length exceeds.');
            }
            _val = newValue;
        };

        // Delete PropertyDescriptor
        if ( delete target[key]) {
            // Create new property with getter and setter
            Object.defineProperty(target, key, {
                get: getter,
                set: setter,
                enumerable: true,
                configurable: true
            });
        }
    };
};

let TableDecoratorFactory = function(name: string, schema?: string){

    let applyMetaKeys = function(target: any){
        let tableMeta: ITableMetaData = {
            name : name,
            schema: schema ? schema : ''
        };

        Reflect.defineMetadata(metadatakeys.Table, tableMeta, target);
        // Reflect.ownKeys
    };

    return function(target: any){
        // save a reference to the original constructor
        let original = target;

        // a utility function to generate instances of a class
        function construct(constructor, args) {
            let c: any = function(){
                return constructor.apply(this, args);
            };

            c.prototype = constructor.prototype;
            return new c();
        };

        // the new constructor behaviour
        let f: any = function(... args ){
            // Do Stuff
            return construct(original, args);
        };

        // copy prototype so intanceof operator still works
        f.prototype = original.prototype;

        applyMetaKeys(f);

        // return new constructor (will override original)
        return f;
    };
};

let ColumnDecoratorFactory = function(name?: string, order?: number){
    function hasNotMapped(target: any, key: string): boolean {
        if (!Reflect.hasOwnMetadata(metadatakeys.NotMapped, target, key)) {
            return false;
        }
        let notMapped: boolean = Reflect.getMetadata(metadatakeys.NotMapped, target, key);
        return notMapped;
    }
    let applyMetaKeys = function(target: any, key: string, nm: string){
        if (hasNotMapped(target, key)) {
            throw new Error('Can not be Combined with "@NotMappd"');
        }
        let keys: IColumnMetaData[] = Reflect.getOwnMetadata(metadatakeys.Column, target) || [];
        let col: IColumnMetaData = {
                key: key,
                name: nm,
                hasChanged: false,
                oldValue: null
            };
        if (order) {
            if (keys[order]) {
                throw new Error('Order already exists!');
            }
            keys[order] = col;
        }
        keys.push(col);
        Reflect.defineMetadata(metadatakeys.Column, keys, target);
        Reflect.defineMetadata(metadatakeys.InnerColumn, {key: key, name: name}, target, key);
    };

    return function(target: any, key: string){
        if (!name) {
            name = key.startsWith('_') ? key.substr(1) : key;
        }

        applyMetaKeys(target, key, name);

        // property Value
        let _val = target[key];

        // property getter
        let getter = function() {
            // Do staff
            return _val;
        };

        // property setter
        let setter = function(newValue){
            // Do staff
            if (_val !== newValue && target.__changed) {
                target.__changed(name, key, _val);
            }
            _val = newValue;
        };

        // Delete PropertyDescriptor
        if ( delete target[key]) {
            // Create new property with getter and setter
            Object.defineProperty(target, key, {
                get: getter,
                set: setter,
                enumerable: true,
                configurable: true
            });
        }
    };
};

let NotMappedDecoratorFactory = function(){
    function hasColumn(target: any, key: string): boolean {
        if (!Reflect.hasOwnMetadata(metadatakeys.InnerColumn, target, key)) {
            return false;
        }
        let notMapped: boolean = Reflect.getMetadata(metadatakeys.InnerColumn, target, key);
        return notMapped;
    }

    let applyMetaKeys = function(target: any, key: string){
        if (hasColumn(target, key)) {
            throw new Error('Can not be Combined with "@Column"');
        }
        Reflect.defineMetadata(metadatakeys.NotMapped, true, target, key);
        // Reflect.ownKeys
    };
    return function(target: any, key: string){

        applyMetaKeys(target, key);

        // property Value
        let _val = target[key];

        // property getter
        let getter = function() {
            // Do staff
            return _val;
        };

        // property setter
        let setter = function(newValue){
            // Do staff
            _val = newValue;
        };

        // Delete PropertyDescriptor
        if ( delete target[key]) {
            // Create new property with getter and setter
            Object.defineProperty(target, key, {
                get: getter,
                set: setter,
                enumerable: true,
                configurable: true
            });
        }
    };
};

let DatabaseGeneratedDecoratorFactory = function(databaseGeneratedOption?: DatabaseGeneratedOption){
    let applyMetaKeys = function(target: any, key: string){
        Reflect.defineMetadata(metadatakeys.NotMapped, '__dbgen__' + key , target, key);
        // Reflect.ownKeys
    };

    return function(target: any, key: string){
        applyMetaKeys(target, key);
        // property Value 
        if (!target['__dbgen__' + key]) {
            target['__dbgen__' + key] = target[key];
        }

        // property getter
        let getter = function() {
            // Do staff
            return this['__dbgen__' + key];
        };

        // property setter
        let setter = function(newValue){
            // Do staff
            // _val = newValue;
        };

        // Delete PropertyDescriptor
        if ( delete target[key]) {
            // Create new property with getter and setter
            Object.defineProperty(target, key, {
                get: getter,
                set: setter,
                enumerable: true,
                configurable: true
            });
        }
    };
};

let InversePropertyDecoratorFactory = function(name: string){
    // TODO: added later
    return function(target: any, key: string){
        // property Value
        let _val = target[key];

        // property getter
        let getter = function() {
            // Do staff
            return _val;
        };

        // property setter
        let setter = function(newValue){
            // Do staff
            _val = newValue;
        };

        // Delete PropertyDescriptor
        if ( delete target[key]) {
            // Create new property with getter and setter
            Object.defineProperty(target, key, {
                get: getter,
                set: setter,
                enumerable: true,
                configurable: true
            });
        }
    };
};

let ForeignKeyDecoratorFactory = function(name: string | string[]){
    function isEntity(target: any): boolean {
        let isEnt = Reflect.getOwnMetadata(metadatakeys.Entry, target) || false;
        return isEnt;
    }

    function NumberofKeys(target: any): number {
        if (!target.__Id__) {
            return 0;
        };

        let s: string[] = target.__Id__;
        return s.length;
    }

    let applyMetaKeys = function(target: any, key: string){
        let tp = Reflect.getMetadata('design:type', target, key);
        let obj = Reflect.construct(tp, null);
        if (!obj) {
            // TODO: check next
            throw new Error('Unknown Type');
        }

        if (!isEntity(obj)) {
            throw new Error('Target is not Entity');
        }
        let names = typeof name === 'string' ? [name] : name;
        names.forEach(element => {
            if (!target[element]) {
                throw new Error('${name} property is unknown');
            }
        });
        if (NumberofKeys(obj) !== name.length) {
          throw new Error('Foreign table key count error!');
        }
        Reflect.defineMetadata(metadatakeys.ForeignKey, names , target, key);
    };
    return function(target: any, key: string){
        applyMetaKeys(target, key);
        // property Value
        let _val = target[key];

        // property getter
        let getter = function() {
            // Do staff
            return _val;
        };

        // property setter
        let setter = function(newValue){
            // Do staff
            _val = newValue;
        };

        // Delete PropertyDescriptor
        if ( delete target[key]) {
            // Create new property with getter and setter
            Object.defineProperty(target, key, {
                get: getter,
                set: setter,
                enumerable: true,
                configurable: true
            });
        }
    };
};

let IndexDecoratorFactory = function(name?: string, isClustered?: boolean, isUnique?: true){
    return function(target: any, key: string){
        // property Value
        let _val = target[key];

        // property getter
        let getter = function() {
            // Do staff
            return _val;
        };

        // property setter
        let setter = function(newValue){
            // Do staff
            _val = newValue;
        };

        // Delete PropertyDescriptor
        if ( delete target[key]) {
            // Create new property with getter and setter
            Object.defineProperty(target, key, {
                get: getter,
                set: setter,
                enumerable: true,
                configurable: true
            });
        }
    };
};

enum DatabaseGeneratedOption {
    Computed,
    Identity,
    None
}

export {
    EntityDecoratorFactory as Entity,
    KeyDecoratorFactory as Id,
    RequiredDecoratorFactory as Required,
    MinLengthDecoratorFactory as MinLength,
    MaxLengthDecoratorFactory as MaxLength,
    TableDecoratorFactory as Table,
    ColumnDecoratorFactory as Column,
    NotMappedDecoratorFactory as NotMapped,
    DatabaseGeneratedDecoratorFactory as DatabaseGenerated,
    InversePropertyDecoratorFactory as InverseProperty,
    ForeignKeyDecoratorFactory as ForeignKey,
    IndexDecoratorFactory as Index,
    DatabaseGeneratedOption,
    metadatakeys,
    ITableMetaData,
    IColumnMetaData
};
