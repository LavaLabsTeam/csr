/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { CsrTestModule } from '../../../test.module';
import { UserMerchantDetailComponent } from '../../../../../../main/webapp/app/entities/user-merchant/user-merchant-detail.component';
import { UserMerchantService } from '../../../../../../main/webapp/app/entities/user-merchant/user-merchant.service';
import { UserMerchant } from '../../../../../../main/webapp/app/entities/user-merchant/user-merchant.model';

describe('Component Tests', () => {

    describe('UserMerchant Management Detail Component', () => {
        let comp: UserMerchantDetailComponent;
        let fixture: ComponentFixture<UserMerchantDetailComponent>;
        let service: UserMerchantService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CsrTestModule],
                declarations: [UserMerchantDetailComponent],
                providers: [
                    UserMerchantService
                ]
            })
            .overrideTemplate(UserMerchantDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(UserMerchantDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UserMerchantService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new UserMerchant(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.userMerchant).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
