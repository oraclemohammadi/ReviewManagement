import { BaseUrls } from './base-urls';
import { Http, Headers, RequestOptions, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
export class BaseService {
    constructor(private _http: Http) {

    }

    protected get Urls(){
        return BaseUrls;
    }

    protected get HttpService(): Http {
        return this._http;
    }

    protected get Headers(): Headers{
        return new Headers( {
            'Content-Type': 'application/x-www-form-urlencoded',
            'Access-Control-Allow-Origin': '*',
            'token': localStorage.getItem('token'),
             'Authorization':"Basic cmV2aWV3dHJhY2tlcmFwcDpteS1zZWNyZXQtdG9rZW4tdG8tY2hhbmdlLWluLXByb2R1Y3Rpb24="
        });
    }

    protected get Options(): RequestOptions{
        return new RequestOptions( { headers: this.Headers});
    }

    protected handleError(error: Response) {
        if (error.text() !== '') {
         return Observable.throw(error.json());
        } else {
            return Observable.throw(error.statusText);
        }
    }
}
