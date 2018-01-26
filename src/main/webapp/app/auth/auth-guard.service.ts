import { AuthenticationService } from './authentication.service';
import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';

@Injectable()
export class AuthGuardService  implements CanActivate {

  constructor(public auth: AuthenticationService, private router: Router) {}
  canActivate(): boolean {
    if (this.auth.isAuthenticated()) {
      return true;
    } else {
      this.router.navigateByUrl('/login');
    }
  }

  canActivateChild(): boolean {
    if (this.auth.isAuthenticated()) {
      return true;
    } else {
      this.router.navigateByUrl('/login');
    }
  }

}