/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { CsrTestModule } from '../../../test.module';
import { MerchantPackageDetailComponent } from '../../../../../../main/webapp/app/entities/merchant-package/merchant-package-detail.component';
import { MerchantPackageService } from '../../../../../../main/webapp/app/entities/merchant-package/merchant-package.service';
import { MerchantPackage } from '../../../../../../main/webapp/app/entities/merchant-package/merchant-package.model';

describe('Component Tests', () => {

    describe('MerchantPackage Management Detail Component', () => {
        let comp: MerchantPackageDetailComponent;
        let fixture: ComponentFixture<MerchantPackageDetailComponent>;
        let service: MerchantPackageService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CsrTestModule],
                declarations: [MerchantPackageDetailComponent],
                providers: [
                    MerchantPackageService
                ]
            })
            .overrideTemplate(MerchantPackageDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MerchantPackageDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MerchantPackageService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new MerchantPackage(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.merchantPackage).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
