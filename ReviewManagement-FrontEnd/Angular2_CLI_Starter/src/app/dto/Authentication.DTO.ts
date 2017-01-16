import {IJsonBase, JsonBaseClass} from './JsonBaseClass.DTO';

interface IAuthenticationDtoJSON extends IJsonBase {
    userName: string;
    password: string;
}

class AuthenticationDto extends JsonBaseClass {
    constructor(private _username: string, private _password: string,
    private _grantType: string, private _clientSecret: string ,private _clinetId:string ) {
        super();
    }

    get userName(): string {
        return this._username;
    }

    set userName(name: string) {
        this._username = name;
    }

    get password(): string {
        return this._password;
    }

    set password(password: string) {
        this._password = password;
    }

    toJson(): IJsonBase {
        return {
            username: this._username,
            password: this._password,
            'grant_type':'password',
            'client_secret':'my-secret-token-to-change-in-production',
            'client_id':'reviewtrackerapp'

        };
    }
}

export {IAuthenticationDtoJSON, AuthenticationDto}
