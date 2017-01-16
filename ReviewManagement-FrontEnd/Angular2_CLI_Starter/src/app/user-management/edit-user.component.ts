import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { UserService } from '../services/user.service';
import { ToasterService, ToasterConfig } from 'angular2-toaster';

class User {
  user: string;
  fullname: string;
  email_address: string;
  password: string;
}

@Component({
  // tslint:disable-next-line:component-selector-prefix
  selector: 'edit-user',
  templateUrl: './edit-user.component.html',
  providers: [UserService],
  styles: [`
    #toast {
      --paper-toast-background-color: red;
      --paper-toast-color: white;
    }
  `]
})
export class EditUserComponent implements OnInit {
  private user: any = {};
  private expense: any = {};
  private _isLoading: boolean;
  public userType = [
    { name: 'Admin', symbol: 'AD' },
    { name: 'Supper Tenant', symbol: 'ST' },
    { name: 'Tenant', symbol: 'TE' }
  ];
  public personType = [
    { name: 'Person', symbol: 'P' },
    { name: 'Company', symbol: 'C' }
  ];
  private selectedPerson: any = {};
  private selectedUser: any = {};

  public toasterConfig: ToasterConfig = new ToasterConfig({
    showCloseButton: { 'warning': true, 'error': false },
    positionClass: 'toast-top-center'
  });

  private responseMessage: string;
  @Output() closeEditor = new EventEmitter();
  constructor(private _userService: UserService, private _toasterService: ToasterService) { }

  public ngOnInit(): void {
  }

  private close(): void {
    // clear form's data
    this.user = '';
    this.closeEditor.emit(false);
  }

  private createUser(): void {
    // TODO: show progress bar
    this._userService.createUserService(this.user)
      .subscribe(res => {
        console.log(res);
        this._isLoading = false;
      }, error => {
        console.log(error);
      }, () => {
        console.log('Job Done Post !');
      });
  }

  private updateUser(): void {
    // TODO: show progress bar
    console.log(JSON.stringify(this.user));
    this._userService.updateUserService(this.user)
      .subscribe(res => {
        console.log(res);
        this._isLoading = false;
        this.responseMessage = res.message;
        this._toasterService.pop('success', 'Args Title', this.responseMessage);

      }, error => {
        console.log(error);
      }, () => {
        console.log('Job Done Post !');
      });
  }

  private onSubmit(updated: any) {
    if (this.selectedPerson){
      this.user.personType = this.selectedPerson.symbol;
    }
    if (this.selectedUser){
      this.user.userType = this.selectedUser.symbol;
    }

    if (this.user.id) {
      this.updateUser();
    }else {
      this.createUser();
    }
  }
}
