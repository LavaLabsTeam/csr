import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Merchant } from './merchant.model';
import { MerchantPopupService } from './merchant-popup.service';
import { MerchantService } from './merchant.service';

@Component({
    selector: 'jhi-merchant-delete-dialog',
    templateUrl: './merchant-delete-dialog.component.html'
})
export class MerchantDeleteDialogComponent {

    merchant: Merchant;

    constructor(
        private merchantService: MerchantService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.merchantService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'merchantListModification',
                content: 'Deleted an merchant'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-merchant-delete-popup',
    template: ''
})
export class MerchantDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private merchantPopupService: MerchantPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.merchantPopupService
                .open(MerchantDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
