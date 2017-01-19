import { Component, OnInit, OnDestroy, AfterViewInit, Input, Output, EventEmitter, ViewChild } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/observable';
import { OrderService } from '../../services/order/order.service';
import { AppConstants } from '../../services/app.constants';
import { ActivatedRoute } from '@angular/router';
//import { CustomerReviewService } from '../../services/customer-review/customer-review.service';
class Order{
  amazonOrderId :String;
  salesChannel:String;
  buyerName:String;
  lastUpdateDate:String;
}
@Component({
  templateUrl: 'order.component.html',
  providers: [OrderService, AppConstants],
  styles: [`
     :host {
      display: flex;
      flex-direction: column;
      height: 100%;
    }
    .content {
      display: flex;
      flex: 1;
    }
    expenses-list {
      flex: 1;
    }
    h1 {
      font-weight: 300;
    }
    overview-panel {
      width: 33%;
      max-width: 300px;
      background: #F2FAF9;
      z-index: 1;
    }
    .toolbar {
      display: flex;
      align-items: center;
      justify-content: space-between;
      padding: 0 18px;
      background: #37474F;
      color: #fff;
      height: 64px;
    }
    .toolbar img {
      margin-left: 12px;
    }
    .toolbar span {
      flex: 1;
      text-align: right;
      font-size: 14px;
      color: #80cbc4;
    }
    paper-dialog {
      display: block;
      padding: 16px 32px;
      border: 1px solid #ccc;
      position: absolute;
      top: 0;
      margin: 0;  
      width: 80vw; /*dialog width*/
      height: 80vh;
      z-index: 0;
    }
    edit-user {
      display: flex;
      flex-direction: column;
      height: 100%;
      margin: 0 !important;
      padding: 0 !important;
    }
    @media (max-width: 600px) {
      paper-dialog {
        width: 100vw;
      }
    }
    @media (max-width: 960px) {
      overview-panel {
        display: none;
      }
    }
    @media (max-width: 600px) {
      h1 {
        font-size: 18px;
      }
    }
    /*inbox style style sheet 
http://www.2my4edge.com/2015/09/google-inbox-style-design-using.html*/
    .fab {
cursor: pointer;
}
.fab-backdrop {
color: rgba(255, 255, 255, 0);
}
.fab-primary, .fab-secondary {
transition: all 0.35s ease-in-out;
}
.fab.active .fab-primary {
opacity: 0;
transform: rotate(225deg);
}
.fab-secondary {
opacity: 0;
transform: rotate(-225deg);
}
.fab.active .fab-secondary {
opacity: 1;
transform: rotate(0);
margin-top: -2px;
}
#inboxstyle .show-on-hover:hover > ul.dropdown-menu {
display: block;    
}
#inboxstyle .show-on-hover {
position: absolute;
top: 0px;      /*changed oringional botomn to top to show + icon on top*/
right: 0px;
z-index: 1000; /*added z-index to be top*/
}
#inboxstyle .btn-io{
border-radius: 50%;
height: 54px;
width: 60px;
padding: 0 !important;
box-shadow: 0px 3px 7px 0px rgba(202, 124, 124, 0.72);
}
#inboxstyle .dropup .dropdown-menu, .navbar-fixed-bottom .dropdown .dropdown-menu {
top:  100%;  /* for showing drop down list on top or down */
bottom: auto;/* for showing drop down list on top or down */
margin-bottom: 1px;
margin-bottom: -5px;
padding-bottom: 30px;
}
#inboxstyle .dropdown-menu-right {
right: 0 !important;
left: auto !important;
}
#inboxstyle .dropdown-menu {
position: absolute;
top: 100%;
left: 0;
z-index: 1000;
display: none;
float: left;
min-width: 50px;
padding: 5px 0;
margin: 2px 0 0;
font-size: 14px;
text-align: center;
list-style: none;
background-color: rgba(255, 255, 255, 0) !important;
-webkit-background-clip: padding-box;
background-clip: padding-box;
border: none;
border-radius: 0px;
-webkit-box-shadow: 0 6px 12px rgba(0, 0, 0, 0) !important;
box-shadow: 0 6px 12px rgba(0, 0, 0, 0) !important;
}
#inboxstyle .fa-iox{
font-size: 22px;
}
#inboxstyle .dropdown-menu > li > a {
display: block;
padding: 0;
padding-top: 4px;
margin-top: 20px;
clear: both;
font-weight: normal;
line-height: 1.42857143;
color: #fff;
background: #824076;
white-space: nowrap;
width: 40px;
height: 40px;
border: solid 1px #ccc;
border-radius: 50px;
font-size: 21px;
box-shadow: 0px 3px 7px 0px rgba(203, 203, 203, 0.72);
}
  `
  ]

})

export class OrderComponent implements OnInit, OnDestroy, AfterViewInit {
  _orders: Order[]=[];
  _asin: any;
  _title: String;
  _sub: any;
  _reviews: any[];
  _selectedOrder:any;
  _orderItems:any[]=[];
   @ViewChild('grid') grid: any;
  constructor(private _orderService: OrderService, private _route: ActivatedRoute) { }
  ngOnInit() {
    this._sub = this._route.params.subscribe(params => {
      this._asin = params['asin']; // (+) converts string 'id' to a number
      this._title = params['title'];
      this.getOrderList(this._asin);
    });

   /* this._customerReviewService.getCustomerReviewList(this._asin).subscribe(res => {
      this._reviews = res;
    });*/


  }

  ngAfterViewInit() {

  }

  ngOnDestroy() {
    this._sub.unsubscribe();
  }
  getOrderList(asin: String) {
    this._orderService.getOrderList(asin).subscribe(res => {
      this._orders = res;
    }, error => {
      console.log(error);

    },
      () => console.log("Job Done Post !"));

  }
  private selected(grid: any) {
   var selection = grid.selection.selected();
   if (selection.length === 1) {
      grid.selection.clear();
      grid.getItem(selection[0], (err: any, item: any) => {
        this._selectedOrder= item;
        this._orderItems=item.orderItems;
      });
    }
  }

}






