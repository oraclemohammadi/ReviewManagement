System.register([], function(exports_1, context_1) {
    "use strict";
    var __moduleName = context_1 && context_1.id;
    var JsonBaseClass;
    return {
        setters:[],
        execute: function() {
            JsonBaseClass = (function () {
                function JsonBaseClass() {
                }
                JsonBaseClass.reviver = function (key, value) {
                    return key === '' ? JsonBaseClass.fromJson(value) : value;
                };
                JsonBaseClass.fromJson = function (json) {
                    if (typeof json === 'string') {
                        return JSON.parse(json, JsonBaseClass.reviver);
                    }
                    else {
                        return JsonBaseClass.fromJsonLocal(json);
                    }
                };
                JsonBaseClass.fromJsonLocal = function (json) {
                    var jsonBaseClass = Object.create(JsonBaseClass.prototype);
                    return Object.assign(jsonBaseClass, json);
                };
                JsonBaseClass.prototype.toJson = function () {
                    return Object.assign({}, this);
                };
                return JsonBaseClass;
            }());
            exports_1("JsonBaseClass", JsonBaseClass);
        }
    }
});
//# sourceMappingURL=JsonBaseClass.DTO.js.map