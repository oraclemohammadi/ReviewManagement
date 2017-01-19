System.register(['@angular/core', '../../services/order/order.service', '../../services/app.constants', '@angular/router', '../../services/customer-review/customer-review.service'], function(exports_1, context_1) {
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
    var core_1, order_service_1, app_constants_1, router_1, customer_review_service_1;
    var Order, OrderComponent;
    return {
        setters:[
            function (core_1_1) {
                core_1 = core_1_1;
            },
            function (order_service_1_1) {
                order_service_1 = order_service_1_1;
            },
            function (app_constants_1_1) {
                app_constants_1 = app_constants_1_1;
            },
            function (router_1_1) {
                router_1 = router_1_1;
            },
            function (customer_review_service_1_1) {
                customer_review_service_1 = customer_review_service_1_1;
            }],
        execute: function() {
            Order = (function () {
                function Order() {
                }
                return Order;
            }());
            OrderComponent = (function () {
                function OrderComponent(_orderService, _route, _customerReviewService) {
                    this._orderService = _orderService;
                    this._route = _route;
                    this._customerReviewService = _customerReviewService;
                    this._orders = [];
                    this._orderItems = [];
                }
                OrderComponent.prototype.ngOnInit = function () {
                    var _this = this;
                    this._sub = this._route.params.subscribe(function (params) {
                        _this._asin = params['asin'];
                        _this._title = params['title'];
                        _this.getOrderList(_this._asin);
                    });
                    this._customerReviewService.getCustomerReviewList(this._asin).subscribe(function (res) {
                        _this._reviews = res;
                    });
                };
                OrderComponent.prototype.ngAfterViewInit = function () {
                };
                OrderComponent.prototype.ngOnDestroy = function () {
                    this._sub.unsubscribe();
                };
                OrderComponent.prototype.getOrderList = function (asin) {
                    var _this = this;
                    this._orderService.getOrderList(asin).subscribe(function (res) {
                        _this._orders = res;
                    }, function (error) {
                        console.log(error);
                    }, function () { return console.log("Job Done Post !"); });
                };
                OrderComponent.prototype.selected = function (grid) {
                    var _this = this;
                    var selection = grid.selection.selected();
                    if (selection.length === 1) {
                        grid.selection.clear();
                        grid.getItem(selection[0], function (err, item) {
                            _this._selectedOrder = item;
                            _this._orderItems = item.orderItems;
                        });
                    }
                };
                __decorate([
                    core_1.ViewChild('grid'), 
                    __metadata('design:type', Object)
                ], OrderComponent.prototype, "grid", void 0);
                OrderComponent = __decorate([
                    core_1.Component({
                        templateUrl: 'app/components/order/order.component.html',
                        providers: [order_service_1.OrderService, app_constants_1.AppConstants, customer_review_service_1.CustomerReviewService],
                        styles: ["\n     :host {\n      display: flex;\n      flex-direction: column;\n      height: 100%;\n    }\n    .content {\n      display: flex;\n      flex: 1;\n    }\n    expenses-list {\n      flex: 1;\n    }\n    h1 {\n      font-weight: 300;\n    }\n    overview-panel {\n      width: 33%;\n      max-width: 300px;\n      background: #F2FAF9;\n      z-index: 1;\n    }\n    .toolbar {\n      display: flex;\n      align-items: center;\n      justify-content: space-between;\n      padding: 0 18px;\n      background: #37474F;\n      color: #fff;\n      height: 64px;\n    }\n    .toolbar img {\n      margin-left: 12px;\n    }\n    .toolbar span {\n      flex: 1;\n      text-align: right;\n      font-size: 14px;\n      color: #80cbc4;\n    }\n    paper-dialog {\n      display: block;\n      padding: 16px 32px;\n      border: 1px solid #ccc;\n      position: absolute;\n      top: 0;\n      margin: 0;  \n      width: 80vw; /*dialog width*/\n      height: 80vh;\n      z-index: 0;\n    }\n    edit-user {\n      display: flex;\n      flex-direction: column;\n      height: 100%;\n      margin: 0 !important;\n      padding: 0 !important;\n    }\n    @media (max-width: 600px) {\n      paper-dialog {\n        width: 100vw;\n      }\n    }\n    @media (max-width: 960px) {\n      overview-panel {\n        display: none;\n      }\n    }\n    @media (max-width: 600px) {\n      h1 {\n        font-size: 18px;\n      }\n    }\n    /*inbox style style sheet \nhttp://www.2my4edge.com/2015/09/google-inbox-style-design-using.html*/\n    .fab {\ncursor: pointer;\n}\n.fab-backdrop {\ncolor: rgba(255, 255, 255, 0);\n}\n.fab-primary, .fab-secondary {\ntransition: all 0.35s ease-in-out;\n}\n.fab.active .fab-primary {\nopacity: 0;\ntransform: rotate(225deg);\n}\n.fab-secondary {\nopacity: 0;\ntransform: rotate(-225deg);\n}\n.fab.active .fab-secondary {\nopacity: 1;\ntransform: rotate(0);\nmargin-top: -2px;\n}\n#inboxstyle .show-on-hover:hover > ul.dropdown-menu {\ndisplay: block;    \n}\n#inboxstyle .show-on-hover {\nposition: absolute;\ntop: 0px;      /*changed oringional botomn to top to show + icon on top*/\nright: 0px;\nz-index: 1000; /*added z-index to be top*/\n}\n#inboxstyle .btn-io{\nborder-radius: 50%;\nheight: 54px;\nwidth: 60px;\npadding: 0 !important;\nbox-shadow: 0px 3px 7px 0px rgba(202, 124, 124, 0.72);\n}\n#inboxstyle .dropup .dropdown-menu, .navbar-fixed-bottom .dropdown .dropdown-menu {\ntop:  100%;  /* for showing drop down list on top or down */\nbottom: auto;/* for showing drop down list on top or down */\nmargin-bottom: 1px;\nmargin-bottom: -5px;\npadding-bottom: 30px;\n}\n#inboxstyle .dropdown-menu-right {\nright: 0 !important;\nleft: auto !important;\n}\n#inboxstyle .dropdown-menu {\nposition: absolute;\ntop: 100%;\nleft: 0;\nz-index: 1000;\ndisplay: none;\nfloat: left;\nmin-width: 50px;\npadding: 5px 0;\nmargin: 2px 0 0;\nfont-size: 14px;\ntext-align: center;\nlist-style: none;\nbackground-color: rgba(255, 255, 255, 0) !important;\n-webkit-background-clip: padding-box;\nbackground-clip: padding-box;\nborder: none;\nborder-radius: 0px;\n-webkit-box-shadow: 0 6px 12px rgba(0, 0, 0, 0) !important;\nbox-shadow: 0 6px 12px rgba(0, 0, 0, 0) !important;\n}\n#inboxstyle .fa-iox{\nfont-size: 22px;\n}\n#inboxstyle .dropdown-menu > li > a {\ndisplay: block;\npadding: 0;\npadding-top: 4px;\nmargin-top: 20px;\nclear: both;\nfont-weight: normal;\nline-height: 1.42857143;\ncolor: #fff;\nbackground: #824076;\nwhite-space: nowrap;\nwidth: 40px;\nheight: 40px;\nborder: solid 1px #ccc;\nborder-radius: 50px;\nfont-size: 21px;\nbox-shadow: 0px 3px 7px 0px rgba(203, 203, 203, 0.72);\n}\n  "
                        ]
                    }), 
                    __metadata('design:paramtypes', [order_service_1.OrderService, router_1.ActivatedRoute, customer_review_service_1.CustomerReviewService])
                ], OrderComponent);
                return OrderComponent;
            }());
            exports_1("OrderComponent", OrderComponent);
        }
    }
});
//# sourceMappingURL=order.component.js.map