import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { MerchantPackageComponent } from './merchant-package.component';
import { MerchantPackageDetailComponent } from './merchant-package-detail.component';
import { MerchantPackagePopupComponent } from './merchant-package-dialog.component';
import { MerchantPackageDeletePopupComponent } from './merchant-package-delete-dialog.component';

@Injectable()
export class MerchantPackageResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const merchantPackageRoute: Routes = [
    {
        path: 'merchant-package',
        component: MerchantPackageComponent,
        resolve: {
            'pagingParams': MerchantPackageResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'csrApp.merchantPackage.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'merchant-package/:id',
        component: MerchantPackageDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'csrApp.merchantPackage.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const merchantPackagePopupRoute: Routes = [
    {
        path: 'merchant-package-new',
        component: MerchantPackagePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'csrApp.merchantPackage.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'merchant-package/:id/edit',
        component: MerchantPackagePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'csrApp.merchantPackage.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'merchant-package/:id/delete',
        component: MerchantPackageDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'csrApp.merchantPackage.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
