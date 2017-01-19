import { Component }        from '@angular/core';
import {Router} from '@angular/router';
import {AuthenticationService } from '../services/authentication.service';

@Component({
    templateUrl: '../pages/login.component.html',
    providers : [AuthenticationService]
})
export class LogoutComponent {
    constructor(private _authenticateService: AuthenticationService, private _router: Router) {
       this._authenticateService.logout().subscribe(res => {
           console.log(res);
           //this._router.navigateByUrl('/pages/login');
           this._router.navigate(['/pages/login']);
        });
    }
}