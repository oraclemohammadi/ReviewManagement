import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { UserLayoutComponent } from './user-layout/user-layout.component';


const routes: Routes = [
    {
        path: '',
        data: {
            title: 'UserManagement'
        },
        children: [
            {
                path: 'users',
                component: UserLayoutComponent,
                pathMatch: 'full'
            }
        ]
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class UserManagementRoutingModule {}
