/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { CsrTestModule } from '../../../test.module';
import { MerchantPackageDialogComponent } from '../../../../../../main/webapp/app/entities/merchant-package/merchant-package-dialog.component';
import { MerchantPackageService } from '../../../../../../main/webapp/app/entities/merchant-package/merchant-package.service';
import { MerchantPackage } from '../../../../../../main/webapp/app/entities/merchant-package/merchant-package.model';
import { MerchantService } from '../../../../../../main/webapp/app/entities/merchant';
import { CategoryService } from '../../../../../../main/webapp/app/entities/category';

describe('Component Tests', () => {

    describe('MerchantPackage Management Dialog Component', () => {
        let comp: MerchantPackageDialogComponent;
        let fixture: ComponentFixture<MerchantPackageDialogComponent>;
        let service: MerchantPackageService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CsrTestModule],
                declarations: [MerchantPackageDialogComponent],
                providers: [
                    MerchantService,
                    CategoryService,
                    MerchantPackageService
                ]
            })
            .overrideTemplate(MerchantPackageDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MerchantPackageDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MerchantPackageService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new MerchantPackage(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.merchantPackage = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'merchantPackageListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new MerchantPackage();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.merchantPackage = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'merchantPackageListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
