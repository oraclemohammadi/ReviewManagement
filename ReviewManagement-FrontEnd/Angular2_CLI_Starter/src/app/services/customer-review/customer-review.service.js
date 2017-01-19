System.register(['@angular/http', 'rxjs/add/operator/map', 'rxjs/add/operator/catch', '@angular/core', 'rxjs/Observable', 'rxjs/add/observable/throw', '../app.constants', 'rxjs/add/operator/toPromise'], function(exports_1, context_1) {
    "use strict";
    var __moduleName = context_1 && context_1.id;
    var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
        var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
        if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
        else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
        return c > 3 && r && Object.defineProperty(target, key, r), r;
    };
    var __metadata = (this && this.__metadata) || function (k, v) {
        if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
    };
    var http_1, core_1, Observable_1, app_constants_1;
    var CustomerReviewService;
    return {
        setters:[
            function (http_1_1) {
                http_1 = http_1_1;
            },
            function (_1) {},
            function (_2) {},
            function (core_1_1) {
                core_1 = core_1_1;
            },
            function (Observable_1_1) {
                Observable_1 = Observable_1_1;
            },
            function (_3) {},
            function (app_constants_1_1) {
                app_constants_1 = app_constants_1_1;
            },
            function (_4) {}],
        execute: function() {
            CustomerReviewService = (function () {
                function CustomerReviewService(_http, appConstants) {
                    this._http = _http;
                    this.appConstants = appConstants;
                }
                CustomerReviewService.prototype.getCustomerReviewList = function (asin) {
                    var body = '{"asin":"' + asin + '"}';
                    var headers = new http_1.Headers();
                    headers.append('Content-Type', 'application/json');
                    headers.append('Access-Control-Allow-Origin', '*');
                    headers.append('token', localStorage.getItem('token'));
                    var options = new http_1.RequestOptions({ headers: headers });
                    return this._http.post(this.appConstants.customerReviewURL, body, options).map(function (res) { return res.json(); }).catch(this.handleError);
                };
                CustomerReviewService.prototype.handleError = function (error) {
                    console.error(error);
                    return Observable_1.Observable.throw(error.json());
                };
                CustomerReviewService = __decorate([
                    core_1.Injectable(), 
                    __metadata('design:paramtypes', [http_1.Http, app_constants_1.AppConstants])
                ], CustomerReviewService);
                return CustomerReviewService;
            }());
            exports_1("CustomerReviewService", CustomerReviewService);
        }
    }
});
//# sourceMappingURL=customer-review.service.js.map