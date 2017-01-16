import { Component, OnInit, AfterViewInit, Output, EventEmitter, ViewChild } from '@angular/core';

import { UserService } from '../services/user.service';

import { Http } from '@angular/http';

declare var HTMLImports: any;
declare var Polymer: any;
class User {
  user: string;
  fullname: string;
  email_address: string;
  password: string;
  staffNo: number;
}
@Component({
  // tslint:disable-next-line:component-selector-prefix
  selector: 'user-expenses-list',
  templateUrl: './user.component.html',
  providers: [UserService]
})
export class UserComponent implements OnInit, AfterViewInit {
  @Output() editExpense = new EventEmitter();
  @ViewChild('grid') grid: any;
  ursers: User[] = [];
  user: User;
  filterUsers: User[] = [];
  _isLoading: boolean;
  _errorMessage: string;

  constructor(private _http: Http, private _userService: UserService) {

  }

  ngOnInit() {
    this.getUsers();
  }

  ngAfterViewInit() {
    // this.refreshItems();
    this.grid.nativeElement.then(() => {
      this.gridReady(this.grid.nativeElement);
    });

  }

  gridReady(grid: any) {
    grid.cellClassGenerator = (cell: any) => {
      if (cell.columnName === 'status') {
        return 'status-' + cell.data.replace(/ /g, '-').toLowerCase();
      }
    };
  }

  getUsers() {
    // TODO show progress bar
    this._userService.getUserListService().subscribe(res => {
      // console.log(res)
      // if (res.messageCode='200')
      // {
      console.log(res);
      this._isLoading = false;
      this.ursers = res;
      // }
    }, error => {
      console.log(error);
      // this._errorMessage=error.fieldErrors[0].message;
      //  console.log(this._errorMessage);
    }, // in case of failure show this message
      () => console.log('Job Done Post !'));
  }

  /* filterUserByName(event){
        const filterText=event.target.value.toLowerCase();
       this.filterUsers= this.users.filter(u:User)=>{
          !filterText||u.user.toLowerCase().indexOf(filterText)>-1
        }
      }*/

  public selected(grid: any) {
    console.log('item selected');
    let selection = grid.selection.selected();
    if (selection.length === 1) {
      grid.selection.clear();
      grid.getItem(selection[0], (err: any, item: any) => {
        this.editExpense.emit(item);
      });
    }
  }

  refreshItems() {
    console.log('refreshing item');
    // This will make grid update it's items (since the datasource changes)
    this.getUsers();
    // Update merchant list
    /*(<any>window).getJSON('./api/merchants', (data: any) => {
      this.merchants = data.sort();
    });*/
  }
}
