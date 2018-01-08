import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { UserMerchant } from './user-merchant.model';
import { UserMerchantPopupService } from './user-merchant-popup.service';
import { UserMerchantService } from './user-merchant.service';
import { User, UserService } from '../../shared';
import { Merchant, MerchantService } from '../merchant';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-user-merchant-dialog',
    templateUrl: './user-merchant-dialog.component.html'
})
export class UserMerchantDialogComponent implements OnInit {

    userMerchant: UserMerchant;
    isSaving: boolean;

    users: User[];

    merchants: Merchant[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private userMerchantService: UserMerchantService,
        private userService: UserService,
        private merchantService: MerchantService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.userService.query()
            .subscribe((res: ResponseWrapper) => { this.users = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.merchantService.query()
            .subscribe((res: ResponseWrapper) => { this.merchants = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.userMerchant.id !== undefined) {
            this.subscribeToSaveResponse(
                this.userMerchantService.update(this.userMerchant));
        } else {
            this.subscribeToSaveResponse(
                this.userMerchantService.create(this.userMerchant));
        }
    }

    private subscribeToSaveResponse(result: Observable<UserMerchant>) {
        result.subscribe((res: UserMerchant) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: UserMerchant) {
        this.eventManager.broadcast({ name: 'userMerchantListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }

    trackMerchantById(index: number, item: Merchant) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-user-merchant-popup',
    template: ''
})
export class UserMerchantPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private userMerchantPopupService: UserMerchantPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.userMerchantPopupService
                    .open(UserMerchantDialogComponent as Component, params['id']);
            } else {
                this.userMerchantPopupService
                    .open(UserMerchantDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
