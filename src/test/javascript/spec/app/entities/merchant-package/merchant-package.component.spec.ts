/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { CsrTestModule } from '../../../test.module';
import { MerchantPackageComponent } from '../../../../../../main/webapp/app/entities/merchant-package/merchant-package.component';
import { MerchantPackageService } from '../../../../../../main/webapp/app/entities/merchant-package/merchant-package.service';
import { MerchantPackage } from '../../../../../../main/webapp/app/entities/merchant-package/merchant-package.model';

describe('Component Tests', () => {

    describe('MerchantPackage Management Component', () => {
        let comp: MerchantPackageComponent;
        let fixture: ComponentFixture<MerchantPackageComponent>;
        let service: MerchantPackageService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CsrTestModule],
                declarations: [MerchantPackageComponent],
                providers: [
                    MerchantPackageService
                ]
            })
            .overrideTemplate(MerchantPackageComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MerchantPackageComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MerchantPackageService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new MerchantPackage(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.merchantPackages[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
