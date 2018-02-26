import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CsrSharedModule } from '../../shared';
import {
    MerchantPackageService,
    MerchantPackagePopupService,
    MerchantPackageComponent,
    MerchantPackageDetailComponent,
    MerchantPackageDialogComponent,
    MerchantPackagePopupComponent,
    MerchantPackageDeletePopupComponent,
    MerchantPackageDeleteDialogComponent,
    merchantPackageRoute,
    merchantPackagePopupRoute,
    MerchantPackageResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...merchantPackageRoute,
    ...merchantPackagePopupRoute,
];

@NgModule({
    imports: [
        CsrSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        MerchantPackageComponent,
        MerchantPackageDetailComponent,
        MerchantPackageDialogComponent,
        MerchantPackageDeleteDialogComponent,
        MerchantPackagePopupComponent,
        MerchantPackageDeletePopupComponent,
    ],
    entryComponents: [
        MerchantPackageComponent,
        MerchantPackageDialogComponent,
        MerchantPackagePopupComponent,
        MerchantPackageDeleteDialogComponent,
        MerchantPackageDeletePopupComponent,
    ],
    providers: [
        MerchantPackageService,
        MerchantPackagePopupService,
        MerchantPackageResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CsrMerchantPackageModule {}
