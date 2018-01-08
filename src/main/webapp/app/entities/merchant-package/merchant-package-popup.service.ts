import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { MerchantPackage } from './merchant-package.model';
import { MerchantPackageService } from './merchant-package.service';

@Injectable()
export class MerchantPackagePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private merchantPackageService: MerchantPackageService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.merchantPackageService.find(id).subscribe((merchantPackage) => {
                    merchantPackage.startDate = this.datePipe
                        .transform(merchantPackage.startDate, 'yyyy-MM-ddTHH:mm:ss');
                    merchantPackage.endDate = this.datePipe
                        .transform(merchantPackage.endDate, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.merchantPackageModalRef(component, merchantPackage);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.merchantPackageModalRef(component, new MerchantPackage());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    merchantPackageModalRef(component: Component, merchantPackage: MerchantPackage): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.merchantPackage = merchantPackage;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
