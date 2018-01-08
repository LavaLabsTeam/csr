import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { UserMerchant } from './user-merchant.model';
import { UserMerchantService } from './user-merchant.service';
import { Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-user-merchant',
    templateUrl: './user-merchant.component.html'
})
export class UserMerchantComponent implements OnInit, OnDestroy {
userMerchants: UserMerchant[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private userMerchantService: UserMerchantService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.userMerchantService.query().subscribe(
            (res: ResponseWrapper) => {
                this.userMerchants = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInUserMerchants();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: UserMerchant) {
        return item.id;
    }
    registerChangeInUserMerchants() {
        this.eventSubscriber = this.eventManager.subscribe('userMerchantListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
