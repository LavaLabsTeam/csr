import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { Merchant } from './merchant.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class MerchantService {

    private resourceUrl = SERVER_API_URL + 'api/merchants';

    constructor(private http: Http) { }

    create(merchant: Merchant): Observable<Merchant> {
        const copy = this.convert(merchant);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(merchant: Merchant): Observable<Merchant> {
        const copy = this.convert(merchant);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Merchant> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to Merchant.
     */
    private convertItemFromServer(json: any): Merchant {
        const entity: Merchant = Object.assign(new Merchant(), json);
        return entity;
    }

    /**
     * Convert a Merchant to a JSON which can be sent to the server.
     */
    private convert(merchant: Merchant): Merchant {
        const copy: Merchant = Object.assign({}, merchant);
        return copy;
    }
}
