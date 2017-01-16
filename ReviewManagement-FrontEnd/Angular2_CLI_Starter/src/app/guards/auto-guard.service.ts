import { Injectable } from '@angular/core';
import { Router, CanActivate } from '@angular/router';
import { AuthenticationService } from '../services/authentication.service';

@Injectable()
export class AutoGuardService implements CanActivate {

  constructor(private router: Router, private authService: AuthenticationService) {
  }

  canActivate(): boolean {
    if (this.authService.isAuthenticated) {
      return true;
    }
    console.log('logged in denied');
    // not logged in so redirect to login page
    this.router.navigate(['/pages/login']);
    return false;
  }
}
