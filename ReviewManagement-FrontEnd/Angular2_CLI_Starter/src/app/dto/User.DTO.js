System.register(['./JsonBaseClass.DTO', '../dataservices/lib/annotations'], function(exports_1, context_1) {
    "use strict";
    var __moduleName = context_1 && context_1.id;
    var __extends = (this && this.__extends) || function (d, b) {
        for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p];
        function __() { this.constructor = d; }
        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
    };
    var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
        var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
        if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
        else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
        return c > 3 && r && Object.defineProperty(target, key, r), r;
    };
    var __metadata = (this && this.__metadata) || function (k, v) {
        if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
    };
    var JsonBaseClass_DTO_1, annotations_1;
    var UserDtoJSON;
    return {
        setters:[
            function (JsonBaseClass_DTO_1_1) {
                JsonBaseClass_DTO_1 = JsonBaseClass_DTO_1_1;
            },
            function (annotations_1_1) {
                annotations_1 = annotations_1_1;
            }],
        execute: function() {
            UserDtoJSON = (function (_super) {
                __extends(UserDtoJSON, _super);
                function UserDtoJSON() {
                    _super.call(this);
                }
                UserDtoJSON.fromJsonLocal = function (json) {
                    var jsonBaseClass = Object.create(UserDtoJSON.prototype);
                    return jsonBaseClass;
                };
                Object.defineProperty(UserDtoJSON.prototype, "id", {
                    get: function () {
                        return this._id;
                    },
                    set: function (value) {
                        this._id = value;
                    },
                    enumerable: true,
                    configurable: true
                });
                Object.defineProperty(UserDtoJSON.prototype, "status", {
                    get: function () {
                        return this._status;
                    },
                    set: function (value) {
                        this._status = value;
                    },
                    enumerable: true,
                    configurable: true
                });
                Object.defineProperty(UserDtoJSON.prototype, "user", {
                    get: function () {
                        return this._username;
                    },
                    set: function (value) {
                        this._username = value;
                    },
                    enumerable: true,
                    configurable: true
                });
                Object.defineProperty(UserDtoJSON.prototype, "password", {
                    get: function () {
                        return this._password;
                    },
                    set: function (value) {
                        this._password = value;
                    },
                    enumerable: true,
                    configurable: true
                });
                Object.defineProperty(UserDtoJSON.prototype, "fullname", {
                    get: function () {
                        return this._fullname;
                    },
                    set: function (value) {
                        this._fullname = value;
                    },
                    enumerable: true,
                    configurable: true
                });
                Object.defineProperty(UserDtoJSON.prototype, "staffNo", {
                    get: function () {
                        return this._staffNo;
                    },
                    set: function (value) {
                        this._staffNo = value;
                    },
                    enumerable: true,
                    configurable: true
                });
                Object.defineProperty(UserDtoJSON.prototype, "departmentId", {
                    get: function () {
                        return this._departmentId;
                    },
                    set: function (value) {
                        this._departmentId = value;
                    },
                    enumerable: true,
                    configurable: true
                });
                Object.defineProperty(UserDtoJSON.prototype, "userGroupId", {
                    get: function () {
                        return this._userGroupId;
                    },
                    set: function (value) {
                        this._userGroupId = value;
                    },
                    enumerable: true,
                    configurable: true
                });
                Object.defineProperty(UserDtoJSON.prototype, "reason", {
                    get: function () {
                        return this._reason;
                    },
                    set: function (value) {
                        this._reason = value;
                    },
                    enumerable: true,
                    configurable: true
                });
                Object.defineProperty(UserDtoJSON.prototype, "createdBy", {
                    get: function () {
                        return this._createdBy;
                    },
                    set: function (value) {
                        this._createdBy = value;
                    },
                    enumerable: true,
                    configurable: true
                });
                Object.defineProperty(UserDtoJSON.prototype, "modifiedBy", {
                    get: function () {
                        return this._modifiedBy;
                    },
                    set: function (value) {
                        this._modifiedBy = value;
                    },
                    enumerable: true,
                    configurable: true
                });
                Object.defineProperty(UserDtoJSON.prototype, "createdDate", {
                    get: function () {
                        return this._createdDate;
                    },
                    set: function (value) {
                        this._createdDate = value;
                    },
                    enumerable: true,
                    configurable: true
                });
                Object.defineProperty(UserDtoJSON.prototype, "modifiedDate", {
                    get: function () {
                        return this._modifiedDate;
                    },
                    set: function (value) {
                        this._modifiedDate = value;
                    },
                    enumerable: true,
                    configurable: true
                });
                Object.defineProperty(UserDtoJSON.prototype, "approveStatus", {
                    get: function () {
                        return this._approveStatus;
                    },
                    set: function (value) {
                        this._approveStatus = value;
                    },
                    enumerable: true,
                    configurable: true
                });
                Object.defineProperty(UserDtoJSON.prototype, "approver", {
                    get: function () {
                        return this._approver;
                    },
                    set: function (value) {
                        this._approver = value;
                    },
                    enumerable: true,
                    configurable: true
                });
                Object.defineProperty(UserDtoJSON.prototype, "approveDate", {
                    get: function () {
                        return this._approveDate;
                    },
                    set: function (value) {
                        this._approveDate = value;
                    },
                    enumerable: true,
                    configurable: true
                });
                Object.defineProperty(UserDtoJSON.prototype, "maker", {
                    get: function () {
                        return this._maker;
                    },
                    set: function (value) {
                        this._maker = value;
                    },
                    enumerable: true,
                    configurable: true
                });
                Object.defineProperty(UserDtoJSON.prototype, "makeDate", {
                    get: function () {
                        return this._makeDate;
                    },
                    set: function (value) {
                        this._makeDate = value;
                    },
                    enumerable: true,
                    configurable: true
                });
                Object.defineProperty(UserDtoJSON.prototype, "lastLogin", {
                    get: function () {
                        return this._lastLogin;
                    },
                    set: function (value) {
                        this._lastLogin = value;
                    },
                    enumerable: true,
                    configurable: true
                });
                UserDtoJSON.prototype.toJson = function () {
                    return {};
                };
                __decorate([
                    annotations_1.Id(),
                    annotations_1.DatabaseGenerated(), 
                    __metadata('design:type', Number)
                ], UserDtoJSON.prototype, "_id", void 0);
                UserDtoJSON = __decorate([
                    annotations_1.Entity(), 
                    __metadata('design:paramtypes', [])
                ], UserDtoJSON);
                return UserDtoJSON;
            }(JsonBaseClass_DTO_1.JsonBaseClass));
            exports_1("UserDtoJSON", UserDtoJSON);
        }
    }
});
//# sourceMappingURL=User.DTO.js.map