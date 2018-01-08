import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CsrSharedModule } from '../../shared';
import {
    MerchantService,
    MerchantPopupService,
    MerchantComponent,
    MerchantDetailComponent,
    MerchantDialogComponent,
    MerchantPopupComponent,
    MerchantDeletePopupComponent,
    MerchantDeleteDialogComponent,
    merchantRoute,
    merchantPopupRoute,
} from './';

const ENTITY_STATES = [
    ...merchantRoute,
    ...merchantPopupRoute,
];

@NgModule({
    imports: [
        CsrSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        MerchantComponent,
        MerchantDetailComponent,
        MerchantDialogComponent,
        MerchantDeleteDialogComponent,
        MerchantPopupComponent,
        MerchantDeletePopupComponent,
    ],
    entryComponents: [
        MerchantComponent,
        MerchantDialogComponent,
        MerchantPopupComponent,
        MerchantDeleteDialogComponent,
        MerchantDeletePopupComponent,
    ],
    providers: [
        MerchantService,
        MerchantPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CsrMerchantModule {}
