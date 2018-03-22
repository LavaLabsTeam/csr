import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { Merchant } from './merchant.model';
import { MerchantService } from './merchant.service';

@Component({
    selector: 'jhi-merchant-detail',
    templateUrl: './merchant-detail.component.html'
})
export class MerchantDetailComponent implements OnInit, OnDestroy {

    merchant: Merchant;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private merchantService: MerchantService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMerchants();
    }

    load(id) {
        this.merchantService.find(id).subscribe((merchant) => {
            this.merchant = merchant;
        });
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMerchants() {
        this.eventSubscriber = this.eventManager.subscribe(
            'merchantListModification',
            (response) => this.load(this.merchant.id)
        );
    }
}
