/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { CsrTestModule } from '../../../test.module';
import { MerchantDialogComponent } from '../../../../../../main/webapp/app/entities/merchant/merchant-dialog.component';
import { MerchantService } from '../../../../../../main/webapp/app/entities/merchant/merchant.service';
import { Merchant } from '../../../../../../main/webapp/app/entities/merchant/merchant.model';

describe('Component Tests', () => {

    describe('Merchant Management Dialog Component', () => {
        let comp: MerchantDialogComponent;
        let fixture: ComponentFixture<MerchantDialogComponent>;
        let service: MerchantService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CsrTestModule],
                declarations: [MerchantDialogComponent],
                providers: [
                    MerchantService
                ]
            })
            .overrideTemplate(MerchantDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MerchantDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MerchantService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Merchant(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.merchant = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'merchantListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Merchant();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.merchant = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'merchantListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
