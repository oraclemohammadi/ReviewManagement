import { NgModule }                 from '@angular/core';

import { ButtonsComponent }         from './buttons.component';
import { CardsComponent }           from './cards.component';
import { FormsComponent }           from './forms.component';
import { SocialButtonsComponent }   from './social-buttons.component';
import { SwitchesComponent }        from './switches.component';
import { TablesComponent }          from './tables.component';

// Modal Component
import { ModalModule }              from 'ng2-bootstrap/modal';
import { ModalsComponent }          from './modals.component';

// Tabs Component
import { TabsModule }               from 'ng2-bootstrap/tabs';
import { TabsComponent }            from './tabs.component';

// Components Routing
import { ComponentsRoutingModule }  from './components-routing.module';
import { OrderComponent } from './order/order.component';
import { PolymerElement } from '@vaadin/angular2-polymer';
import {ControlMessagesComponent}       from './control-message.component';

import {ValidationService} from '../services/validation.service';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common'; //this one added for ngIf in control-messeage component
import { LoginComponent } from './login.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';  //for login page and all pages that are using forms


@NgModule({
    imports: [
        ComponentsRoutingModule,
        ModalModule.forRoot(),
        TabsModule,
        CommonModule,
        FormsModule,
        ReactiveFormsModule
        
        
    ],
    exports:[OrderComponent,ControlMessagesComponent,LoginComponent],
    declarations: [
        ButtonsComponent,
        CardsComponent,
        FormsComponent,
        ModalsComponent,
        SocialButtonsComponent,
        SwitchesComponent,
        TablesComponent,
        TabsComponent,
        //add custom project here
        OrderComponent,
        PolymerElement('vaadin-grid'),
        PolymerElement('paper-dialog'),
        ControlMessagesComponent,
        LoginComponent

    ],
    providers:[ValidationService],
     schemas: [ CUSTOM_ELEMENTS_SCHEMA ]  //necessary for using non-standard and custome properties  eg vaadin-polymer
})
export class ComponentsModule { }
