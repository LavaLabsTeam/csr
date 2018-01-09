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

@NgModule({
    imports: [
        BrowserModule,
        Ng2Webstorage.forRoot({ prefix: 'jhi', separator: '-'}),
        AppRoutingModule
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
        PackageComponent
    ],
    providers: [],
    bootstrap: [ AppComponent ]
})
export class CsrAppModule {}
