System.register(['./JsonBaseClass.DTO'], function(exports_1, context_1) {
    "use strict";
    var __moduleName = context_1 && context_1.id;
    var __extends = (this && this.__extends) || function (d, b) {
        for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p];
        function __() { this.constructor = d; }
        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
    };
    var JsonBaseClass_DTO_1;
    var AuthenticationDto;
    return {
        setters:[
            function (JsonBaseClass_DTO_1_1) {
                JsonBaseClass_DTO_1 = JsonBaseClass_DTO_1_1;
            }],
        execute: function() {
            AuthenticationDto = (function (_super) {
                __extends(AuthenticationDto, _super);
                function AuthenticationDto(_userName, _password) {
                    _super.call(this);
                    this._userName = _userName;
                    this._password = _password;
                }
                Object.defineProperty(AuthenticationDto.prototype, "userName", {
                    get: function () {
                        return this._userName;
                    },
                    set: function (name) {
                        this._userName = name;
                    },
                    enumerable: true,
                    configurable: true
                });
                Object.defineProperty(AuthenticationDto.prototype, "password", {
                    get: function () {
                        return this._password;
                    },
                    set: function (password) {
                        this._password = password;
                    },
                    enumerable: true,
                    configurable: true
                });
                AuthenticationDto.prototype.toJson = function () {
                    return {
                        userName: this._userName,
                        password: this._password
                    };
                };
                return AuthenticationDto;
            }(JsonBaseClass_DTO_1.JsonBaseClass));
            exports_1("AuthenticationDto", AuthenticationDto);
        }
    }
});
//# sourceMappingURL=Authentication.DTO.js.map