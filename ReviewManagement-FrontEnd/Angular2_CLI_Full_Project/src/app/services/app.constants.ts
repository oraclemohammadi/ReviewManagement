import { Injectable } from '@angular/core';
@Injectable()
export class AppConstants{

    readonly API_END_POINT="http://localhost:8080/";
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
}
const API = {
    userList: '/usercar/details=true',
    loginService: '/api/user/login',
    appSettingMetaData:'/api/metaData/SettingMetaDataList',
    customerReviewURL:'api/reviews',
    productURL:'/api/product/listProduct',
    orderListURL:'/api/purchase-orders'
}