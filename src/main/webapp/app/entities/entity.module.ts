import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { CsrCategoryModule } from './category/category.module';
import { CsrMerchantModule } from './merchant/merchant.module';
import { CsrUserMerchantModule } from './user-merchant/user-merchant.module';
import { CsrMerchantPackageModule } from './merchant-package/merchant-package.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        CsrCategoryModule,
        CsrMerchantModule,
        CsrUserMerchantModule,
        CsrMerchantPackageModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CsrEntityModule {}
