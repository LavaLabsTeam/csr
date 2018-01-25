import { TokenInterceptor } from './auth/token-interceptor';
import { AuthenticationService } from './auth/authentication.service';
import { AuthGuardService } from './auth/auth-guard.service';
import { HomeComponent } from './public/home/home.component';
import { AppRoutingModule } from './app-routing/app-routing.module';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { Ng2Webstorage } from 'ngx-webstorage';
import { AppComponent } from './app.component';
import { HeaderComponent } from './public/layout/header/header.component';
import { ContentComponent } from './public/layout/content/content.component';
import { FooterComponent } from './public/layout/footer/footer.component';
import { LandingComponent } from './public/landing/landing.component';
import { MerchantComponent } from './public/merchant/merchant.component';
import { PackageComponent } from './public/package/package.component';
import { LoginComponent } from './public/login/login.component';
import { SidebarComponent } from './dashboard/layout/sidebar/sidebar.component';
import { AdminHeaderComponent } from './dashboard/layout/admin-header/admin-header.component';
import { AdminFooterComponent } from './dashboard/layout/admin-footer/admin-footer.component';
import { AdminContentComponent } from './dashboard/layout/admin-content/admin-content.component';
import { AdminHomeComponent } from './dashboard/admin-home/admin-home.component';
import { AdminMerchantComponent } from './dashboard/admin-merchant/admin-merchant.component';
import { AdminCategoryComponent } from './dashboard/admin-category/admin-category.component';
import { AdminPackageComponent } from './dashboard/admin-package/admin-package.component';
import { AddMerchantComponent } from './dashboard/admin-merchant/add-merchant/add-merchant.component';
import { AddCategoryComponent } from './dashboard/admin-category/add-category/add-category.component';
import { AddPackageComponent } from './dashboard/admin-package/add-package/add-package.component';
import { AlertModule } from 'ngx-bootstrap';
import { HTTP_INTERCEPTORS } from '@angular/common/http';

@NgModule({
    imports: [
        BrowserModule,
        Ng2Webstorage.forRoot({ prefix: 'jhi', separator: '-'}),
        AppRoutingModule,
        AlertModule.forRoot()
        // jhipster-needle-angular-add-module JHipster will add new module here
    ],
    declarations: [
        AppComponent,
        HomeComponent,
        HeaderComponent,
        ContentComponent,
        FooterComponent,
        LandingComponent,
        MerchantComponent,
        PackageComponent,
        LoginComponent,
        SidebarComponent,
        AdminHeaderComponent,
        AdminFooterComponent,
        AdminContentComponent,
        AdminHomeComponent,
        AdminMerchantComponent,
        AdminCategoryComponent,
        AdminPackageComponent,
        AddMerchantComponent,
        AddCategoryComponent,
        AddPackageComponent
    ],
    providers: [
        AuthGuardService,
        AuthenticationService,
        {
        provide: HTTP_INTERCEPTORS,
        useClass: TokenInterceptor,
        multi: true
        }
    ],
    bootstrap: [ AppComponent ]
})
export class CsrAppModule {}
