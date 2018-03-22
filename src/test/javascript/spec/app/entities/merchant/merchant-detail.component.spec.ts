/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { CsrTestModule } from '../../../test.module';
import { MerchantDetailComponent } from '../../../../../../main/webapp/app/entities/merchant/merchant-detail.component';
import { MerchantService } from '../../../../../../main/webapp/app/entities/merchant/merchant.service';
import { Merchant } from '../../../../../../main/webapp/app/entities/merchant/merchant.model';

describe('Component Tests', () => {

    describe('Merchant Management Detail Component', () => {
        let comp: MerchantDetailComponent;
        let fixture: ComponentFixture<MerchantDetailComponent>;
        let service: MerchantService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CsrTestModule],
                declarations: [MerchantDetailComponent],
                providers: [
                    MerchantService
                ]
            })
            .overrideTemplate(MerchantDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MerchantDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MerchantService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new Merchant(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.merchant).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
