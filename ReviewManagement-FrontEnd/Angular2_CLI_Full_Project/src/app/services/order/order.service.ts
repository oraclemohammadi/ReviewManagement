import {Http, Headers, RequestOptions, Response} from '@angular/http';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import {Injectable,} from '@angular/core'
import {Observable}     from 'rxjs/Observable';
import 'rxjs/add/observable/throw';
import {AppConstants} from '../app.constants';
//import {TreeModule,TreeNode} from 'primeng/primeng';
import 'rxjs/add/operator/toPromise';
@Injectable()
export class OrderService{
constructor(private _http:Http, private appConstants:AppConstants){

}
getOrderList(asin:String){
    let body = '{"asin":"'+asin+'"}';
    let headers = new Headers();
    headers.append('Content-Type', 'application/json');
    headers.append('Access-Control-Allow-Origin', '*');
    headers.append('Authorization','Bearer'+' '+sessionStorage.getItem('token'));
    let options = new RequestOptions({ headers: headers });
    return this._http.get(this.appConstants.OrderListURL,options).map(res=>res.json()).catch(this.handleError);
  }
  private handleError (error: Response) {
        console.error(error);
        return Observable.throw(error.json());
   }

  

}
