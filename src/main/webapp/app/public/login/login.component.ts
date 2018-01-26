import { UserMerchantService } from './../merchant/user-merchant.service';
import { AuthenticationService } from './../../auth/authentication.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'jhi-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  constructor(public auth: AuthenticationService, private userMerchantService: UserMerchantService) {
  }

  ngOnInit() {
  }

  onLoginClicked() {
    this.auth.loginAdmin({username: 'admin', password: 'admin'}).subscribe((res: any) => {
      console.log(res);
    });
  }

  onForgotClicked() {
    this.userMerchantService.getAll().subscribe((res: any) => {
      console.log(res);
    });
  }

}
