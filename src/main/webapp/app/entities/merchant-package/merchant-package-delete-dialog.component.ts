import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { MerchantPackage } from './merchant-package.model';
import { MerchantPackagePopupService } from './merchant-package-popup.service';
import { MerchantPackageService } from './merchant-package.service';

@Component({
    selector: 'jhi-merchant-package-delete-dialog',
    templateUrl: './merchant-package-delete-dialog.component.html'
})
export class MerchantPackageDeleteDialogComponent {

    merchantPackage: MerchantPackage;

    constructor(
        private merchantPackageService: MerchantPackageService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.merchantPackageService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'merchantPackageListModification',
                content: 'Deleted an merchantPackage'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-merchant-package-delete-popup',
    template: ''
})
export class MerchantPackageDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private merchantPackagePopupService: MerchantPackagePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.merchantPackagePopupService
                .open(MerchantPackageDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
