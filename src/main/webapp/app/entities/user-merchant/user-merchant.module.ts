import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CsrSharedModule } from '../../shared';
import { CsrAdminModule } from '../../admin/admin.module';
import {
    UserMerchantService,
    UserMerchantPopupService,
    UserMerchantComponent,
    UserMerchantDetailComponent,
    UserMerchantDialogComponent,
    UserMerchantPopupComponent,
    UserMerchantDeletePopupComponent,
    UserMerchantDeleteDialogComponent,
    userMerchantRoute,
    userMerchantPopupRoute,
} from './';

const ENTITY_STATES = [
    ...userMerchantRoute,
    ...userMerchantPopupRoute,
];

@NgModule({
    imports: [
        CsrSharedModule,
        CsrAdminModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        UserMerchantComponent,
        UserMerchantDetailComponent,
        UserMerchantDialogComponent,
        UserMerchantDeleteDialogComponent,
        UserMerchantPopupComponent,
        UserMerchantDeletePopupComponent,
    ],
    entryComponents: [
        UserMerchantComponent,
        UserMerchantDialogComponent,
        UserMerchantPopupComponent,
        UserMerchantDeleteDialogComponent,
        UserMerchantDeletePopupComponent,
    ],
    providers: [
        UserMerchantService,
        UserMerchantPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CsrUserMerchantModule {}
