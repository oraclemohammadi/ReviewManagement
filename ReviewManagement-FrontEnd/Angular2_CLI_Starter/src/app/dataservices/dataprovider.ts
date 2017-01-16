import { RestServiceProvider} from './lib/providers/restservice.provider';
import { MappingUtility} from './lib/mapping.utility';
import { DataUserService } from '../services/data-user.service';
import { Inject, ReflectiveInjector } from '@angular/core';
import { Http,
         CookieXSRFStrategy,
         ConnectionBackend,
         BrowserXhr,
         XHRBackend,
         ResponseOptions,
         XSRFStrategy,
         RequestOptions,
         Headers } from '@angular/http';


export class DataProvider extends RestServiceProvider {

    @Inject(DataUserService)
    private _dataUserService: DataUserService;

    constructor() {
        super('RestDataPorvider', MappingUtility.getMapping, MappingUtility.getTableName);
        let injector = ReflectiveInjector.resolveAndCreate( [
            DataUserService,
            Http,
            {
                provide: ConnectionBackend,
                useClass: XHRBackend
            },
            BrowserXhr,
            {
                provide: ResponseOptions,
                useFactory: () => {
                    return new ResponseOptions({
                    });
                },
            },
            {
                provide: XSRFStrategy,
                useValue: new CookieXSRFStrategy('site', 'header')
            },
            {
                provide: RequestOptions,
                useFactory: () => {
                    return new RequestOptions({
                        headers: new Headers()
                    });
                },
            }
        ]);
        this._dataUserService = injector.get(DataUserService);
        this.fillServices();
    }

    private fillServices(): void {
        this.services = {
            UserDto: {
                getAll: this._dataUserService.getUserList,
                getEntity: null,
                update: null,
                insert: null,
                delete: null,
            }
        };
    }
}
