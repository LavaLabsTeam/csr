import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { UserMerchant } from './user-merchant.model';
import { UserMerchantPopupService } from './user-merchant-popup.service';
import { UserMerchantService } from './user-merchant.service';

@Component({
    selector: 'jhi-user-merchant-delete-dialog',
    templateUrl: './user-merchant-delete-dialog.component.html'
})
export class UserMerchantDeleteDialogComponent {

    userMerchant: UserMerchant;

    constructor(
        private userMerchantService: UserMerchantService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.userMerchantService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'userMerchantListModification',
                content: 'Deleted an userMerchant'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-user-merchant-delete-popup',
    template: ''
})
export class UserMerchantDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private userMerchantPopupService: UserMerchantPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.userMerchantPopupService
                .open(UserMerchantDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
