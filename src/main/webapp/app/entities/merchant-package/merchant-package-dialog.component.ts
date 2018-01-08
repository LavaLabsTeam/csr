import { Component, OnInit, OnDestroy, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { MerchantPackage } from './merchant-package.model';
import { MerchantPackagePopupService } from './merchant-package-popup.service';
import { MerchantPackageService } from './merchant-package.service';
import { Merchant, MerchantService } from '../merchant';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-merchant-package-dialog',
    templateUrl: './merchant-package-dialog.component.html'
})
export class MerchantPackageDialogComponent implements OnInit {

    merchantPackage: MerchantPackage;
    isSaving: boolean;

    merchants: Merchant[];

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private merchantPackageService: MerchantPackageService,
        private merchantService: MerchantService,
        private elementRef: ElementRef,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.merchantService.query()
            .subscribe((res: ResponseWrapper) => { this.merchants = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.merchantPackage, this.elementRef, field, fieldContentType, idInput);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.merchantPackage.id !== undefined) {
            this.subscribeToSaveResponse(
                this.merchantPackageService.update(this.merchantPackage));
        } else {
            this.subscribeToSaveResponse(
                this.merchantPackageService.create(this.merchantPackage));
        }
    }

    private subscribeToSaveResponse(result: Observable<MerchantPackage>) {
        result.subscribe((res: MerchantPackage) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: MerchantPackage) {
        this.eventManager.broadcast({ name: 'merchantPackageListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackMerchantById(index: number, item: Merchant) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-merchant-package-popup',
    template: ''
})
export class MerchantPackagePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private merchantPackagePopupService: MerchantPackagePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.merchantPackagePopupService
                    .open(MerchantPackageDialogComponent as Component, params['id']);
            } else {
                this.merchantPackagePopupService
                    .open(MerchantPackageDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
