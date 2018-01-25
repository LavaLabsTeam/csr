import { SERVER_API_URL } from './../app.constants'
import { Injectable } from '@angular/core';
import { Http, Headers, RequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Rx';

@Injectable()
export class AuthenticationService {
    public STRADM= 'admin';
    public STRAGN= 'user';
    authoroties= [];
    private permissions: any= [
       {
        role: 'admin',
        roles: [
            {
                moduleName: 'agent',
                read: true,
                create: false,
                update: true,
                remove: true,
                verify: true
            }

        ]
       },
       {
        role: 'user',
        roles: [
            {
                moduleName: 'agent',
                read: true,
                create: true,
                update: true,
                remove: false,
                verify: false
            }
        ]
       }
    ];
    constructor( private http: Http) { }

    public getToken(): string {
        return sessionStorage.getItem( 'jwt_token' );
    }

    public getRefreshToken(): string {
        return sessionStorage.getItem( 'refresh_token' );
    }

    public isAuthenticated(): boolean {
        const token = sessionStorage.getItem( 'jwt_token' );
        return token != null;
    }

    public getAuthRole(): string {
        const type = sessionStorage.getItem('role_token');
        return type;
    }

    public getUserId(): string {
        const type = sessionStorage.getItem('profile_id');
        return type;
    }

    public isAdmin(): boolean {

        return this.getAuthRole() === btoa(this.STRADM);
    }

    public isUser(): boolean {

        return this.getAuthRole() === btoa(this.STRAGN);
    }

    public can(module: string, action: string): boolean {

        const userType = atob(this.getAuthRole());

        let can = false;
        can = this._can(userType, module, action);

        return can;

    }

    private _can(userType: string, module: string, action: string) {
        let can = false;
        const roles = this.permissions.filter((per) => {
            return per.role === userType;
        }).map((res) => {
            return res.roles;
        });

        for (const r of roles){
            for (const r1 of r){
                if (r1.moduleName === module) {
                can = r1[action];
                }

            }
        }

        return can;
    }

  // public refreshToken () {

  //     let h = new Headers(
  //         {
  //             'Accept': 'application/json',
  //             'Content-Type': 'application/x-www-form-urlencoded',
  //         } );
  //     let options = new RequestOptions( { headers: h } );
  //     let body = `refresh_token=${ this.getRefreshToken() }&grant_type=refresh_token&client_id=${ client_id }&client_secret=${ client_secret }`;

  //     return this.http.post( '/api/oauth/token', body, options ).do( resp => {
  //         if ( resp ) {
  //             sessionStorage.setItem( 'access_token', resp.json().access_token );
  //             sessionStorage.setItem( 'refresh_token', resp.json().refresh_token );
  //         }
  //     } ).catch( error => {
  //         return Observable.of( false );
  //     } );
  // }

  public loginUser(body) {
    const h = new Headers(
        {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        } );
    const options = new RequestOptions( { headers: h } );
    // let body = `username=${ username }&password=${ password }`;

    return this.http.post( SERVER_API_URL + '/agent/authenticate', body, options ).do( (resp) => {
        console.log(resp);
        if ( resp ) {
          sessionStorage.setItem( 'jwt_token', resp.json().id_token );
          sessionStorage.setItem( 'role_token', btoa(this.STRAGN) );
          // sessionStorage.setItem( 'refresh_token', resp.json().refresh_token );
        }
    } ).catch( (error) => {
        return Observable.of( false );
    } );
  }

  public loginAdmin(body) {

    const h = new Headers(
        {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        } );
    const options = new RequestOptions( { headers: h } );
    // let body = `username=${ username }&password=${ password }`;

    return this.http.post( SERVER_API_URL + '/authenticate', body, options ).do( (resp) => {
        if ( resp ) {
          sessionStorage.setItem( 'jwt_token', resp.json().id_token );
          sessionStorage.setItem( 'role_token', btoa(this.STRADM) );
          // sessionStorage.setItem( 'refresh_token', resp.json().refresh_token );
        }
    } ).catch( (error) => {
        return Observable.of( false );
    } );
  }

}
