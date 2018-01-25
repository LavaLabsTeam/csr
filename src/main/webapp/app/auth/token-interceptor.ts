import { AuthenticationService } from './authentication.service';
import { Router } from '@angular/router';
import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor, HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';

@Injectable()
export class TokenInterceptor implements HttpInterceptor {

    constructor( public auth: AuthenticationService, private router: Router ) { }

    intercept( request: HttpRequest<any>, next: HttpHandler ): Observable<HttpEvent<any>> {

        const req = request.clone( {
            setHeaders: {
                Accept: 'application/json',
                'Content-Type': 'application/json',
                Authorization: `Bearer ${ this.auth.getToken() }`
            }
        } );

        return next.handle( req ).do(( event: HttpEvent<any> ) => {
            if ( event instanceof HttpResponse ) {
                // do stuff with response if you want
            }
        }, ( err: any ) => {
            if ( err instanceof HttpErrorResponse ) {
                if ( err.status === 401 || err.status === 403 ) {
                    // this.auth.refreshToken().subscribe( resp => {
                    //     if ( !resp ) {
                    //         this.router.navigateByUrl("/login");
                    //         sessionStorage.clear();
                    //     } else {
                    //         var retryRequest = request.clone( {
                    //             setHeaders: {
                    //                 Accept: 'application/json',
                    //                 'Content-Type': 'application/json',
                    //                 Authorization: `Bearer ${ this.auth.getToken() }`
                    //             }
                    //         } );
                    //         //console.log("sssss");
                    //         //console.log(retryRequest);

                    //         return next.handle( retryRequest ).subscribe(res=> {
                    //             //alert("daaa");
                    //         });
                    //     }
                    // } );
                    if (this.auth.getAuthRole() === btoa(this.auth.STRADM)) {
                        this.router.navigateByUrl('/admin/login');
                    } else {
                        this.router.navigateByUrl('/login');
                    }

                    sessionStorage.clear();

                }
            }
        } );
    }
}
