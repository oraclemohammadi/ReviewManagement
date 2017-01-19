import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../services/authentication.service';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthenticationDto } from '../dto/authentication.DTO';
//import { ToasterContainerComponent, ToasterConfig, ToasterService, Toast } from 'angular2-toaster/angular2-toaster';


@Component({
  templateUrl: './login.component.html',
  providers: [AuthenticationService
  //, ToasterService
  ]
})
export class LoginComponent implements OnInit {
  private response: any;
  private signinForm: FormGroup;
  private _isLoading: boolean;
  private _errorMessage: string;
  //private _toast: Toast;

 /* public toastConfig: ToasterConfig = new ToasterConfig({
    showCloseButton: { 'warning': true, 'error': false },
    positionClass: 'toast-top-bottom'
  });*/

  constructor(private _authService: AuthenticationService,
    private formBuilder: FormBuilder,
    private _router: Router
    
    ) {
      this.signinForm = new FormGroup({
      Username: new FormControl('', Validators.required),
      Password: new FormControl('', Validators.required)
    });
     }

  ngOnInit() {
    
  }

  logForm(value: any): void {
    console.log('logform is calling');
    //if (this.signinForm.valid) {
      // TODO show progress bar
      this._isLoading = true;
      let login = new AuthenticationDto(value.Username, value.Password,'password','my-secret-token-to-change-in-production','reviewtrackerapp');
      this._authService.login(login)
        .subscribe(res => {
          console.log(res);
          this.response = res;
          this._router.navigateByUrl('');
        }, error => {
          // when server is up or can not communicate with server
          if (!error) {
            error = 'Unable to connect to server.';
          }
         // this._toast = this._toastService.pop('error', 'Error : ', error);
          // this._toasterService.clear(this._toast.toastId, this._toast.toastContainerId);
        }, // in case of failure show this message
        () => console.log('Job Done Post!'));
    }
  //}

}
