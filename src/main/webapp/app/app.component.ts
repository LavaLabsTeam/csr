import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRouteSnapshot, NavigationEnd } from '@angular/router';

@Component({
    selector: 'jhi-main',
    templateUrl: './app.component.html'
})
export class AppComponent implements OnInit {

    constructor(
        private router: Router
    ) {}

   
    ngOnInit() {
        
    }
}