import {IJsonBase, JsonBaseClass} from './JsonBaseClass.DTO';

import {Entity, Id, Required, MinLength, MaxLength, Table, Column, NotMapped,
    DatabaseGenerated, ForeignKey} from '../dataservices/lib/annotations';

interface IUserDto extends IJsonBase {
    id: number;
    status: string;
    user: string;
    password: string;
    fullname?: string;
    staffNo?: string;
    departmentId?: number;
    userGroupId?: number;
    reason?: string;
    createdBy?: string;
    modifiedBy?: string;
    createdDate?: Date;
    modifiedDate?: Date;
    approveStatus?: string;
    approver?: string;
    approveDate?: Date;
    maker?: string;
    makeDate?: Date;
    lastLogin?: Date;
}
@Entity()
class UserDto extends JsonBaseClass {
    @Id()
    @DatabaseGenerated()
    private _id: number;
    private _status: string;
    private _username: string;
    private _password: string;
    private _fullname: string;
    private _staffNo: string;
    private _departmentId: number;
    private _userGroupId: number;
    private _reason: string;
    private _createdBy: string;
    private _modifiedBy: string;
    private _createdDate: Date;
    private _modifiedDate: Date;
    private _approveStatus: string;
    private _approver: string;
    private _approveDate: Date;
    private _maker: string;
    private _makeDate: Date;
    private _lastLogin: Date;

    public static fromJsonLocal(json: IJsonBase): UserDto {
        let jsonBaseClass = Object.create(UserDto.prototype);

        return jsonBaseClass;
    }

    constructor() {
        super();

        // Javascript bug so automapper does not find the property
        this._id = null;
        this._status = null;
        this._username = null;
        this._password = null;
        this._fullname = null;
        this._staffNo = null;
        this._departmentId = null;
        this._userGroupId = null;
        this._reason = null;
        this._createdBy = null;
        this._modifiedBy = null;
        this._createdDate = null;
        this._modifiedDate = null;
        this._approveStatus = null;
        this._approver = null;
        this._approveDate = null;
        this._maker = null;
        this._makeDate = null;
        this._lastLogin = null;
    }

    public get id(): number{
        return this._id;
    }

    public set id(value: number){
        this._id = value;
    }

    public get status(): string{
        return  this._status;
    }

    public set status(value: string){
        this._status = value;
    }

    public get user(): string {
        return this._username;
    }
    public set user(value: string) {
        this._username = value;
    }
    public get password(): string {
        return this._password;
    }
    public set password(value: string) {
        this._password = value;
    }
    public get fullname(): string {
        return this._fullname;
    }
    public set fullname(value: string) {
        this._fullname = value;
    }
    public get staffNo(): string {
        return this._staffNo;
    }
    public set staffNo(value: string) {
        this._staffNo = value;
    }
    public get departmentId(): number {
        return this._departmentId;
    }
    public set departmentId(value: number) {
        this._departmentId = value;
    }
    public get userGroupId(): number {
        return this._userGroupId;
    }
    public set userGroupId(value: number) {
        this._userGroupId = value;
    }
    public get reason(): string {
        return this._reason;
    }
    public set reason(value: string) {
        this._reason = value;
    }
    public get createdBy(): string {
        return this._createdBy;
    }
    public set createdBy(value: string) {
        this._createdBy = value;
    }
    public get modifiedBy(): string {
        return this._modifiedBy;
    }
    public set modifiedBy(value: string) {
        this._modifiedBy = value;
    }
    public get createdDate(): Date {
        return this._createdDate;
    }
    public set createdDate(value: Date) {
        this._createdDate = value;
    }
    public get modifiedDate(): Date {
        return this._modifiedDate;
    }
    public set modifiedDate(value: Date) {
        this._modifiedDate = value;
    }
    public get approveStatus(): string {
        return this._approveStatus;
    }
    public set approveStatus(value: string) {
        this._approveStatus = value;
    }
    public get approver(): string {
        return this._approver;
    }
    public set approver(value: string) {
        this._approver = value;
    }
    public get approveDate(): Date {
        return this._approveDate;
    }
    public set approveDate(value: Date) {
        this._approveDate = value;
    }
    public get maker(): string {
        return this._maker;
    }
    public set maker(value: string) {
        this._maker = value;
    }
    public get makeDate(): Date {
        return this._makeDate;
    }
    public set makeDate(value: Date) {
        this._makeDate = value;
    }
    public get lastLogin(): Date {
        return this._lastLogin;
    }
    public set lastLogin(value: Date) {
        this._lastLogin = value;
    }

    public toJson(): IJsonBase {
        return {

        };
    }


}

export { IUserDto, UserDto};
