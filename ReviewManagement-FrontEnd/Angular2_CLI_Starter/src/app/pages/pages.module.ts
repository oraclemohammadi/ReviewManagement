import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

// components
import { p500Component }            from './500.component';
import { p404Component }            from './404.component';
import { LoginComponent }           from './login.component';
import { RegisterComponent }        from './register.component';

import { PagesRoutingModule }       from './pages.routing.module';

import { ToasterModule, ToasterService } from 'angular2-toaster';


@NgModule({
  imports: [
    PagesRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    ToasterModule
  ],
  declarations: [
    LoginComponent,
    p500Component,
    p404Component,
    RegisterComponent,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA],
    providers: [ToasterService]
})
export class PagesModule { }
