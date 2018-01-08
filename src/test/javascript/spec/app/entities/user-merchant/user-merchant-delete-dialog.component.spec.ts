/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { CsrTestModule } from '../../../test.module';
import { UserMerchantDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/user-merchant/user-merchant-delete-dialog.component';
import { UserMerchantService } from '../../../../../../main/webapp/app/entities/user-merchant/user-merchant.service';

describe('Component Tests', () => {

    describe('UserMerchant Management Delete Component', () => {
        let comp: UserMerchantDeleteDialogComponent;
        let fixture: ComponentFixture<UserMerchantDeleteDialogComponent>;
        let service: UserMerchantService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CsrTestModule],
                declarations: [UserMerchantDeleteDialogComponent],
                providers: [
                    UserMerchantService
                ]
            })
            .overrideTemplate(UserMerchantDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(UserMerchantDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UserMerchantService);
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
