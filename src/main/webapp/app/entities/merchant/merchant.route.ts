import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { MerchantComponent } from './merchant.component';
import { MerchantDetailComponent } from './merchant-detail.component';
import { MerchantPopupComponent } from './merchant-dialog.component';
import { MerchantDeletePopupComponent } from './merchant-delete-dialog.component';

export const merchantRoute: Routes = [
    {
        path: 'merchant',
        component: MerchantComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'csrApp.merchant.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'merchant/:id',
        component: MerchantDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'csrApp.merchant.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const merchantPopupRoute: Routes = [
    {
        path: 'merchant-new',
        component: MerchantPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'csrApp.merchant.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'merchant/:id/edit',
        component: MerchantPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'csrApp.merchant.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'merchant/:id/delete',
        component: MerchantDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'csrApp.merchant.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
