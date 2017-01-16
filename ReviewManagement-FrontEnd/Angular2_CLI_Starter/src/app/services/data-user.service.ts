import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import { BaseService } from './base-service';
import { IUserDto } from '../dto/User.DTO';

@Injectable()
export class DataUserService extends BaseService {

  private _self: DataUserService;

  constructor(_http: Http) {
    super(_http);
    this._self = this;
    this.getUserList = this.getUserList.bind(this);
  }

  public getUserList(): Promise<IUserDto> {
    let self = this._self;
    return new Promise<IUserDto>((resolve, reject) => {
      let body = JSON.stringify({
        'page' : '0',
        'size' : '40',
        'sortDirection': 'ASC',
        'sortByProperties': [],
        'searchCriteria':[]
        });
      self.HttpService.post(self.Urls.userListUrl, body , self.Options)
      .map(res => {
        return res.json();
      })
      .catch(this.handleError)
      .subscribe(resp => {
        resolve(resp);
      }, (error) => {
        reject(error);
      });
    });
  }
}
