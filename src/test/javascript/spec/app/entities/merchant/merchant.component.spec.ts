/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { CsrTestModule } from '../../../test.module';
import { MerchantComponent } from '../../../../../../main/webapp/app/entities/merchant/merchant.component';
import { MerchantService } from '../../../../../../main/webapp/app/entities/merchant/merchant.service';
import { Merchant } from '../../../../../../main/webapp/app/entities/merchant/merchant.model';

describe('Component Tests', () => {

    describe('Merchant Management Component', () => {
        let comp: MerchantComponent;
        let fixture: ComponentFixture<MerchantComponent>;
        let service: MerchantService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CsrTestModule],
                declarations: [MerchantComponent],
                providers: [
                    MerchantService
                ]
            })
            .overrideTemplate(MerchantComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MerchantComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MerchantService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new Merchant(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.merchants[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
