import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { Merchant } from './merchant.model';
import { MerchantService } from './merchant.service';
import { Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-merchant',
    templateUrl: './merchant.component.html'
})
export class MerchantComponent implements OnInit, OnDestroy {
merchants: Merchant[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private merchantService: MerchantService,
        private jhiAlertService: JhiAlertService,
        private dataUtils: JhiDataUtils,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.merchantService.query().subscribe(
            (res: ResponseWrapper) => {
                this.merchants = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInMerchants();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Merchant) {
        return item.id;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    registerChangeInMerchants() {
        this.eventSubscriber = this.eventManager.subscribe('merchantListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
