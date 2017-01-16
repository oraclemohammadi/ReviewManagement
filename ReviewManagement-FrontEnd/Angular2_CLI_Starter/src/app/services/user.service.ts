import {Http} from '@angular/http';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import {Injectable, } from '@angular/core';
import 'rxjs/add/observable/throw';
import {BaseService} from './base-service';

class User {
    user: string;
    fullname: string;
    email_address: string;
    passwod: string;
    staffNo: number;
    }
@Injectable()
export class UserService extends BaseService {
  constructor(_http: Http)  {
    super(_http);
   }

  getUserListService() {
    let body = JSON.stringify({
    'page' : '0',
    'size' : '40',
    'sortDirection': 'ASC',
    'sortByProperties':
    [],
    'searchCriteria':
        [

        ]

        });
    return this.HttpService.post(this.Urls.userListUrl, body, this.Options)
    .map(res => res.json())
    .catch(this.handleError);
  }
  createUserService(_user: User) {
    let body = JSON.stringify(_user);
    return this.HttpService.post(this.Urls.createUserUrl, body, this.Options)
    .map(res => res.json())
    .catch(this.handleError);
  }
  updateUserService(_user: User) {
    let body = JSON.stringify(_user);
    return this.HttpService.post(this.Urls.updateUserUrl, body, this.Options)
    .map(res => res.json())
    .catch(this.handleError);
  }
}
