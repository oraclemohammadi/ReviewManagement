import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { UserManagementRoutingModule } from './user-management-routing.module';
import { UserComponent} from './user.component';
import { EditUserComponent } from './edit-user.component';
import { UserLayoutComponent }  from './user-layout/user-layout.component';
import { PolymerElement } from '@vaadin/angular2-polymer';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { UserListComponent } from './user-list.component';
import { HttpModule } from '@angular/http';

@NgModule({
  imports: [
    UserManagementRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpModule
  ],
  declarations: [
    UserLayoutComponent,
    UserComponent,
    EditUserComponent,
    PolymerElement('paper-dialog'),
    PolymerElement('paper-input'),
    PolymerElement('vaadin-grid'),
    PolymerElement('paper-icon-button'),
    PolymerElement('paper-button'),
    PolymerElement('paper-toast'),
    PolymerElement('paper-checkbox'),
    PolymerElement('paper-textarea'),
    UserListComponent,
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class UserManagementModule { }
