import { NgModule } from '@angular/core';
import { Routes, RouterModule} from '@angular/router';

import { LoginComponent} from './login.component';
import { p404Component } from './404.component';
import { p500Component } from './500.component';
import { RegisterComponent } from './register.component';

const routes: Routes = [
    {
        path: '',
        data: {
            title: 'Pages'
        },
        children: [
            {
                path: 'login',
                component: LoginComponent,
                data: {
                    title: 'Login Page'
                }
            },
            {
                path: '404',
                component: p404Component,
                data: {
                    title: 'Not Found!'
                }
            },
            {
                path: '500',
                component: p500Component,
                data: {
                    title: 'Internal error!'
                }
            },
            {
                path: 'register',
                component: RegisterComponent,
                data: {
                    title: 'Register User'
                }
            }
        ]
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class PagesRoutingModule {}
