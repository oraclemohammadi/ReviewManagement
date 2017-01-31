import { Injectable } from '@angular/core';

@Injectable()
export class AppConstants{
    constructor(){
      console.log('path'+window.location.protocol+"//"+window.location.host);
    }
    //readonly API_END_POINT=window.location.protocol+"//"+window.location.host+'/reviewtracker-0.0.1-SNAPSHOT';
    readonly API_END_POINT="http://localhost:8080";
    get loginServiceURL() {
        return `${this.API_END_POINT}${API.loginService}`
    }
    get appSettingMetaDataURL() {
        return `${this.API_END_POINT}${API.appSettingMetaData}`
    }
    get customerReviewURL() {
        return `${this.API_END_POINT}${API.customerReviewURL}`
    }
    get productListURL() {
        return `${this.API_END_POINT}${API.productURL}`
    }
    get OrderListURL() {
        return `${this.API_END_POINT}${API.orderListURL}`
    }
    get contactReviewerURL() {
        return `${this.API_END_POINT}${API.contactReviewerURL}`
    }
}
const API = {
    userList: '/usercar/details=true',
    loginService: '/api/user/login',
    appSettingMetaData:'/api/metaData/SettingMetaDataList',
    customerReviewURL:'/api/reviews',
    contactReviewerURL:'/api/contactReviewer',
    productURL:'/api/product/listProduct',
    orderListURL:'/api/purchase-orders'
}