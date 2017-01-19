interface IJsonBase {

}

/**
* toJson is automatically used by JSON.stringify
*/
abstract class JsonBaseClass {

    /**
     * reviver can be passed as the second parameter to JSON.parse
     * to automatically call JsonBaseClass.fromJSON on the resulting value.
     */
    public static reviver(key: string, value: any): any {
        return key === '' ? JsonBaseClass.fromJson(value) : value;
    }

    public static fromJson(json: IJsonBase | string): JsonBaseClass {
        if (typeof json === 'string') {
            // if it's a string, parse it first
            return JSON.parse(json, JsonBaseClass.reviver);
        }else {
            return JsonBaseClass.fromJsonLocal(json);
        }
    }

    public static fromJsonLocal(json: IJsonBase) {
        // Create an instance of the JsonBaseClass
        let jsonBaseClass = Object.create(JsonBaseClass.prototype);
        // copy all the fields from the json Object
        return Object.assign(jsonBaseClass, json);
    }

    public toJson(): IJsonBase {
        return Object.assign({}, this);
    }
}

export{IJsonBase, JsonBaseClass}
