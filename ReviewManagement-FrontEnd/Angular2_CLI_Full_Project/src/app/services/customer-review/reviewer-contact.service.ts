import {Http, Headers, RequestOptions, Response} from '@angular/http';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import 'rxjs/add/observable/frompromise';
import {Injectable,} from '@angular/core'
import {Observable}     from 'rxjs/Observable';
import 'rxjs/add/observable/throw';
import {AppConstants} from '../app.constants';
//import {TreeModule,TreeNode} from 'primeng/primeng';

@Injectable()
export class ReviewerContactService{
constructor(private _http:Http, private appConstants:AppConstants){

}
sendEmailToReviewer(orderId:String,marketPlaceId:String,buyerId:String,message:String){
   console.log('review called');
    let body = '{"orderId":"'+orderId+
    '","marketPlaceId":"'+marketPlaceId+
    '","buyerId":"'+buyerId+
    '","message":"'+message+
    '"}';
    let headers = new Headers();
    headers.append('Content-Type', 'application/json');
    headers.append('Access-Control-Allow-Origin', '*');
    headers.append('Authorization','Bearer'+' '+sessionStorage.getItem('token'));
    let options = new RequestOptions({ headers: headers });
    return this._http.post(this.appConstants.contactReviewerURL,body,options).map(this.mapResponse)
    .catch(this.handleError);
    
    
  }
  private handleError (error: Response) {
        console.error(error);
        return Observable.throw(error.json());
   }
  private mapResponse(response: Response) {
    return {data:response.json(),status:response.status,headers:response.headers};
  }
  

}
