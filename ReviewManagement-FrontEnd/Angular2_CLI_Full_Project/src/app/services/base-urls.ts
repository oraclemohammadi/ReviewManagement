export class BaseUrls {
    private static readonly _backendpoint: string = 'http://localhost:8080/';
    public static loginUrl: string = BaseUrls._backendpoint + 'oauth/token';
    public static logoutUrl:  string = BaseUrls._backendpoint + 'api/logout';
    public static userListUrl:  string = BaseUrls._backendpoint + 'api/user/users';
    public static createUserUrl:  string = BaseUrls._backendpoint + 'api/user/addUser';
    public static updateUserUrl:  string = BaseUrls._backendpoint + 'api/user/updateUser';
    public static appSettingMetaData:  string = BaseUrls._backendpoint + 'api/metaData/SettingMetaDataList';
}
