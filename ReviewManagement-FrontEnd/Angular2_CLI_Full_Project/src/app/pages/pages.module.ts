import { NgModule } from '@angular/core';

import { p404Component } from './404.component';
import { p500Component } from './500.component';
import { RegisterComponent } from './register.component';
import { PagesRoutingModule } from './pages-routing.module';

//add project custom
import { FormsModule, ReactiveFormsModule } from '@angular/forms';  //for login page and all pages that are using forms
import { LogoutComponent } from '../components/logout.component';
import { LoginComponent } from '../components/login.component';


@NgModule({
  imports: [ PagesRoutingModule,
        //add project custom     
        FormsModule,
        ReactiveFormsModule ],
  exports:[LogoutComponent,LoginComponent],
  declarations: [
    p404Component,
    p500Component,
    RegisterComponent,
    //add project cusstom here
    LogoutComponent,
    LoginComponent
    

  ]
})
export class PagesModule { }
