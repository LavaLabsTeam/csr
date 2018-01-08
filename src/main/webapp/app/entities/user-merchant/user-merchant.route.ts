import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { UserMerchantComponent } from './user-merchant.component';
import { UserMerchantDetailComponent } from './user-merchant-detail.component';
import { UserMerchantPopupComponent } from './user-merchant-dialog.component';
import { UserMerchantDeletePopupComponent } from './user-merchant-delete-dialog.component';

export const userMerchantRoute: Routes = [
    {
        path: 'user-merchant',
        component: UserMerchantComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'csrApp.userMerchant.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'user-merchant/:id',
        component: UserMerchantDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'csrApp.userMerchant.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const userMerchantPopupRoute: Routes = [
    {
        path: 'user-merchant-new',
        component: UserMerchantPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'csrApp.userMerchant.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'user-merchant/:id/edit',
        component: UserMerchantPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'csrApp.userMerchant.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'user-merchant/:id/delete',
        component: UserMerchantDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'csrApp.userMerchant.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
