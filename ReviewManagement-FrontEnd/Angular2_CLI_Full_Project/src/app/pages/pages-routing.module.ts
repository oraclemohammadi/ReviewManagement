import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { p404Component } from './404.component';
import { p500Component } from './500.component';
//import { LoginComponent } from '../components/login.component';
import { RegisterComponent } from './register.component';
//add project's custom compoenents here
import { LogoutComponent } from '../components/logout.component';

const routes: Routes = [
  {
    path: '',
    data: {
      title: 'Example Pages'
    },
    children: [
      {
        path: '404',
        component: p404Component,
        data: {
          title: 'Page 404'
        }
      },
      {
        path: '500',
        component: p500Component,
        data: {
          title: 'Page 500'
        }
      },
     /* {
        path: 'login',
        component: LoginComponent,
        data: {
          title: 'Login Page'
        }
      },*/
      {
        path: 'register',
        component: RegisterComponent,
        data: {
          title: 'Register Page'
        }
      },
      {
        path: 'logout',
        component: LogoutComponent,
        data: {
          title: 'logout'
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
