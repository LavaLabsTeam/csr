import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { MerchantPackage } from './merchant-package.model';
import { MerchantPackageService } from './merchant-package.service';

@Component({
    selector: 'jhi-merchant-package-detail',
    templateUrl: './merchant-package-detail.component.html'
})
export class MerchantPackageDetailComponent implements OnInit, OnDestroy {

    merchantPackage: MerchantPackage;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private merchantPackageService: MerchantPackageService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMerchantPackages();
    }

    load(id) {
        this.merchantPackageService.find(id).subscribe((merchantPackage) => {
            this.merchantPackage = merchantPackage;
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

    registerChangeInMerchantPackages() {
        this.eventSubscriber = this.eventManager.subscribe(
            'merchantPackageListModification',
            (response) => this.load(this.merchantPackage.id)
        );
    }
}
