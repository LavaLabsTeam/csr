import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { UserMerchant } from './user-merchant.model';
import { UserMerchantService } from './user-merchant.service';

@Component({
    selector: 'jhi-user-merchant-detail',
    templateUrl: './user-merchant-detail.component.html'
})
export class UserMerchantDetailComponent implements OnInit, OnDestroy {

    userMerchant: UserMerchant;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private userMerchantService: UserMerchantService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInUserMerchants();
    }

    load(id) {
        this.userMerchantService.find(id).subscribe((userMerchant) => {
            this.userMerchant = userMerchant;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInUserMerchants() {
        this.eventSubscriber = this.eventManager.subscribe(
            'userMerchantListModification',
            (response) => this.load(this.userMerchant.id)
        );
    }
}
