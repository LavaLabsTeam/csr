/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { CsrTestModule } from '../../../test.module';
import { UserMerchantDialogComponent } from '../../../../../../main/webapp/app/entities/user-merchant/user-merchant-dialog.component';
import { UserMerchantService } from '../../../../../../main/webapp/app/entities/user-merchant/user-merchant.service';
import { UserMerchant } from '../../../../../../main/webapp/app/entities/user-merchant/user-merchant.model';
import { UserService } from '../../../../../../main/webapp/app/shared';
import { MerchantService } from '../../../../../../main/webapp/app/entities/merchant';

describe('Component Tests', () => {

    describe('UserMerchant Management Dialog Component', () => {
        let comp: UserMerchantDialogComponent;
        let fixture: ComponentFixture<UserMerchantDialogComponent>;
        let service: UserMerchantService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CsrTestModule],
                declarations: [UserMerchantDialogComponent],
                providers: [
                    UserService,
                    MerchantService,
                    UserMerchantService
                ]
            })
            .overrideTemplate(UserMerchantDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(UserMerchantDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UserMerchantService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new UserMerchant(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.userMerchant = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'userMerchantListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new UserMerchant();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.userMerchant = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'userMerchantListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
