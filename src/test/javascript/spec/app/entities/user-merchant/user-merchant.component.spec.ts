/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { CsrTestModule } from '../../../test.module';
import { UserMerchantComponent } from '../../../../../../main/webapp/app/entities/user-merchant/user-merchant.component';
import { UserMerchantService } from '../../../../../../main/webapp/app/entities/user-merchant/user-merchant.service';
import { UserMerchant } from '../../../../../../main/webapp/app/entities/user-merchant/user-merchant.model';

describe('Component Tests', () => {

    describe('UserMerchant Management Component', () => {
        let comp: UserMerchantComponent;
        let fixture: ComponentFixture<UserMerchantComponent>;
        let service: UserMerchantService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CsrTestModule],
                declarations: [UserMerchantComponent],
                providers: [
                    UserMerchantService
                ]
            })
            .overrideTemplate(UserMerchantComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(UserMerchantComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UserMerchantService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new UserMerchant(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.userMerchants[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
