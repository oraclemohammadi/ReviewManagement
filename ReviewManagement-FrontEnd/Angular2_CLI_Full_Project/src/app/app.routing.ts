import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

// Layouts
import { FullLayoutComponent } from './layouts/full-layout.component';
import { SimpleLayoutComponent }  from './layouts/simple-layout.component';

//new custom services
import { AutoGuardService } from './guards/auto-guard.service';

export const routes: Routes = [
  {
    path: '',
    redirectTo: 'dashboard',
    pathMatch: 'full',
    canActivate: [AutoGuardService]  //redirect to login page
  },
  {
    path: '',
    component: FullLayoutComponent,
    data: {
      title: 'Home'
    },
    canActivate: [AutoGuardService],
    children: [
      {
        path: 'dashboard',
        loadChildren: 'app/dashboard/dashboard.module#DashboardModule'
      },
      {
        path: 'components',
        loadChildren: 'app/components/components.module#ComponentsModule'
      },
      {
        path: 'icons',
        loadChildren: 'app/icons/icons.module#IconsModule'
      },
      {
        path: 'widgets',
        loadChildren: 'app/widgets/widgets.module#WidgetsModule'
      },
      {
        path: 'charts',
        loadChildren: 'app/chartjs/chartjs.module#ChartJSModule'
      }
    ]
  },
  {
    path: 'pages',
    component: SimpleLayoutComponent,
    data: {
      title: 'Pages'
    },
    children: [
      {
        path: '',
        loadChildren: 'app/pages/pages.module#PagesModule',
      }
    ]
  }
];

@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {}
