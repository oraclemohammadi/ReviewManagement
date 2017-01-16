import { NgModule }                 from '@angular/core';
import { Routes,
         RouterModule }             from '@angular/router';

// Layouts
import { FullLayoutComponent }      from './layouts/full-layout.component';
import { SimpleLayoutComponent } from './layouts/simple-layout.component';

import { AutoGuardService } from './guards/auto-guard.service';

export const routes: Routes = [
    {
        path: '',
        redirectTo: 'dashboard',
        pathMatch: 'full',
        canActivate: [AutoGuardService]
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
                loadChildren: 'app/pages/pages.module#PagesModule'
            }
        ]
    },
    {
        path: 'components',
        component: FullLayoutComponent,
        data: {
            title: 'Components'
        },
        children: [
            {
                path: '',
                loadChildren: 'app/components/components.module#ComponentsModule'
            }
        ]
    },
    {
        path: 'usermanagement',
        component: FullLayoutComponent,
        data: {
            title: 'UserManagement'
        },
        children: [
            {
                path: '',
                loadChildren: 'app/user-management/user-management.module#UserManagementModule'
            }
        ]
    }
];

@NgModule({
    imports: [ RouterModule.forRoot(routes) ],
    exports: [ RouterModule ]
})
export class AppRoutingModule {}
