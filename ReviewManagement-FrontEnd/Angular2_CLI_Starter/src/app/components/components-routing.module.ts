import { NgModule } from '@angular/core';
import { Routes, RouterModule} from '@angular/router';

import { LogoutComponent} from './logout.component';

const routes: Routes = [
    {
        path: '',
        data: {
            title: 'Components'
        },
        children: [
            {
                path: 'logout',
                component: LogoutComponent,
                data: {
                    title: 'Login Page'
                }
            }
        ]
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class ComponentsRoutingModule {}
