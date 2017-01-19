import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { JsonBaseClass } from '../dto/JsonBaseClass.DTO';
import { Http, Response } from '@angular/http';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import 'rxjs/add/observable/frompromise';
import { BaseService } from './base-service';
import {Headers,URLSearchParams } from '@angular/http';

@Injectable()
export class AuthenticationService extends BaseService {
  private _loginUrl = this.Urls.loginUrl;

  constructor(http: Http) {
    super(http);
  }

  public login(obj: JsonBaseClass): Observable<boolean> {
    let body = new URLSearchParams();
  body.append('username', 'user');
  body.append('password', 'user');
   body.append('grant_type', 'password');
    body.append('client_secret', 'my-secret-token-to-change-in-production');
     body.append('client_id', 'reviewtrackerapp');
    let options = {
            headers: new Headers({
                'credentials': 'true',
                'grant_type': 'password',
                'scope': 'write',
                'Accept': 'application/json',
                'Content-Type': 'application/form',
                'Authorization':"Basic " + btoa('user' + ":" + 'user')
            })
        };
    let promise = new Promise((resolve, reject) => {
      this.HttpService.post(this._loginUrl, body, this.Options)
        .map(this.mapLogin)
        .catch(this.handleError)
        .subscribe(data => {
          if (this.afterLogin(data, obj)) {
            resolve(true);
          } else {
            reject({ messageCode: data.messageCode });
          }
        }, error => {
          reject(this.errorPromise(error));
        });
    });
    return Observable.fromPromise(promise);
  }

  public logout(): Observable<boolean> {
    let promise = new Promise((resolve, reject) => {
      this.HttpService.get(this.Urls.logoutUrl)
        .map((res:any) => res.status)
        .subscribe(data => {
          if (data.status === 200) {
            sessionStorage.removeItem('isAuth');
            sessionStorage.removeItem('currentUser');
            sessionStorage.removeItem('token');
            resolve(true);
          } else {
            reject({ messageCode: data.messageCode });
          }
        }, error => {
          reject(this.errorPromise(error));
        });
    });
    return Observable.fromPromise(promise);
  }

  public get isAuthenticated(): boolean {
    if (sessionStorage.getItem('isAuth') !== null) {
      console.log('User has logged in');
      return true;
    }
    return false;
  }

  private afterLogin(response: any, input: any): boolean {
    if (response.status === 200) {
      console.log('authenticated');
      sessionStorage.setItem('isAuth', 'true');
      sessionStorage.setItem('currentUser', input.userName);
      sessionStorage.setItem('token', response.access_token);
      return true;
    }
    return false;
  }

  private errorPromise(error: any): string {
    let errorMessage = '';
    if (error.fieldErrors) {
      errorMessage = error.fieldErrors[0].message;
    } else {
      errorMessage = error.responseText;
    }
    return errorMessage;
  }

  private mapLogin(response: Response) {
    return {data:response.json(),status:response.status};
  }
}
