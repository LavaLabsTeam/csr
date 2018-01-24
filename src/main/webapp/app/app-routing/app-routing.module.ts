import { AddCategoryComponent } from './../dashboard/admin-category/add-category/add-category.component';
import { AddPackageComponent } from './../dashboard/admin-package/add-package/add-package.component';
import { AddMerchantComponent } from './../dashboard/admin-merchant/add-merchant/add-merchant.component';
import { AdminPackageComponent } from './../dashboard/admin-package/admin-package.component';
import { AdminHeaderComponent } from './../dashboard/layout/admin-header/admin-header.component';
import { AdminContentComponent } from './../dashboard/layout/admin-content/admin-content.component';
import { LoginComponent } from './../public/login/login.component';

import { PackageComponent } from './../public/package/package.component';
import { LandingComponent } from './../public/landing/landing.component';
import { HomeComponent } from './../public/home/home.component';
import { NgModule } from '@angular/core';
import { RouterModule, Route } from '@angular/router';
import { ContentComponent } from './../public/layout/content/content.component';
import { MerchantComponent } from './../public/merchant/merchant.component';
import { AdminHomeComponent } from './../dashboard/admin-home/admin-home.component';
import { AdminMerchantComponent } from './../dashboard/admin-merchant/admin-merchant.component';
import { AdminCategoryComponent } from './../dashboard/admin-category/admin-category.component';

const ROUTES: Array<Route> = [{
  path: 'landing',
  component: LandingComponent,
  pathMatch: 'full'
},
{
  path: 'login',
  component: LoginComponent,
  pathMatch: 'full'
},
{
  path: '',
  component: ContentComponent,
  children: [
    { path: '',  component: HomeComponent,  pathMatch: 'full'},
    { path: 'merchants/:id',  component: MerchantComponent,  pathMatch: 'full'},
    { path: 'packages/:id',  component: PackageComponent,  pathMatch: 'full'}
  ]
},
{
  path: 'dashboard',
  component: AdminContentComponent,
  children: [
    { path: '',  component: AdminHomeComponent,  pathMatch: 'full'},
    { path: 'merchants',  component: AdminMerchantComponent,  pathMatch: 'full'},
    { path: 'merchants/add',  component: AddMerchantComponent,  pathMatch: 'full'},
    { path: 'merchants/edit/:id',  component: AddMerchantComponent,  pathMatch: 'full'},
    { path: 'packages',  component: AdminPackageComponent,  pathMatch: 'full'},
    { path: 'packages/add',  component: AddPackageComponent,  pathMatch: 'full'},
    { path: 'packages/edit/:id',  component: AddPackageComponent,  pathMatch: 'full'},
    { path: 'categories',  component: AdminCategoryComponent,  pathMatch: 'full'},
    { path: 'categories/add',  component: AddCategoryComponent,  pathMatch: 'full'},
    { path: 'categories/edit/:id',  component: AddCategoryComponent,  pathMatch: 'full'},
  ]
}];

@NgModule({
  imports: [
    RouterModule.forRoot(ROUTES,  { useHash: true })
  ],
  declarations: [],
  exports: [RouterModule]
})
export class AppRoutingModule { }
