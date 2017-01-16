import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { HttpModule } from '@angular/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { BrowserModule } from '@angular/platform-browser';

import {
    LocationStrategy,
    HashLocationStrategy,
    PathLocationStrategy
} from '@angular/common';

import { AppComponent } from './app.component';
import { Ng2BootstrapModule } from 'ng2-bootstrap/ng2-bootstrap';
import { NAV_DROPDOWN_DIRECTIVES } from './shared/nav-dropdown.directive';

import { ChartsModule } from 'ng2-charts/ng2-charts';
import { SIDEBAR_TOGGLE_DIRECTIVES } from './shared/sidebar.directive';
import { AsideToggleDirective } from './shared/aside.directive';
import { BreadcrumbsComponent } from './shared/breadcrumb.component';

// Routing Module
import { AppRoutingModule } from './app.routing';

// Layouts
import { FullLayoutComponent } from './layouts/full-layout.component';
import { SimpleLayoutComponent } from './layouts/simple-layout.component';

// Project Added Module
import { PolymerElement } from '@vaadin/angular2-polymer';
import { ToasterModule, ToasterService } from 'angular2-toaster';
import { AutoGuardService } from './guards/auto-guard.service';
import { AuthenticationService } from './services/authentication.service';
// import { UserLayoutComponent } from './layouts/user-layout/user-layout.component';

@NgModule({
    imports: [
        BrowserModule,
        AppRoutingModule,
        Ng2BootstrapModule,
        ChartsModule,
        HttpModule,
        FormsModule,
        ReactiveFormsModule,
        ToasterModule
    ],
    declarations: [
        AppComponent,
        FullLayoutComponent,
        SimpleLayoutComponent,
        NAV_DROPDOWN_DIRECTIVES,
        BreadcrumbsComponent,
        SIDEBAR_TOGGLE_DIRECTIVES,
        AsideToggleDirective,
        // Add to corresponing modules        
    ],
    providers: [{
        provide: LocationStrategy,
        useClass: PathLocationStrategy
    }, AutoGuardService, ToasterService, AuthenticationService],
    bootstrap: [AppComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppModule { }
