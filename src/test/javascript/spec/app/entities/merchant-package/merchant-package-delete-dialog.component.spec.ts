/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { CsrTestModule } from '../../../test.module';
import { MerchantPackageDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/merchant-package/merchant-package-delete-dialog.component';
import { MerchantPackageService } from '../../../../../../main/webapp/app/entities/merchant-package/merchant-package.service';

describe('Component Tests', () => {

    describe('MerchantPackage Management Delete Component', () => {
        let comp: MerchantPackageDeleteDialogComponent;
        let fixture: ComponentFixture<MerchantPackageDeleteDialogComponent>;
        let service: MerchantPackageService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CsrTestModule],
                declarations: [MerchantPackageDeleteDialogComponent],
                providers: [
                    MerchantPackageService
                ]
            })
            .overrideTemplate(MerchantPackageDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MerchantPackageDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MerchantPackageService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
