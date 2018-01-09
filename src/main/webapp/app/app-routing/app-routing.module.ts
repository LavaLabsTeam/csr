import { PackageComponent } from './../public/package/package.component';
import { LandingComponent } from './../public/landing/landing.component';
import { HomeComponent } from './../public/home/home.component';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { ContentComponent } from './../public/layout/content/content.component';
import { MerchantComponent } from './../public/merchant/merchant.component';


@NgModule({
  imports: [
    RouterModule.forRoot([
      { 
        path: 'landing', 
        component: LandingComponent, 
        pathMatch: 'full'
      },
      { 
        path: '', 
        component: ContentComponent, 
        children:[
          { path: '',  component: HomeComponent,  pathMatch: 'full'},
          { path: 'merchants/:id',  component: MerchantComponent,  pathMatch: 'full'},
          { path: 'packages/:id',  component: PackageComponent,  pathMatch: 'full'},
        ]

      },
      //{ path: 'home',  component: HomeComponent, pathMatch: 'full'},   
    ],  { useHash: true })
  ],
  declarations: [],
  exports: [ RouterModule]
})
export class AppRoutingModule { }
