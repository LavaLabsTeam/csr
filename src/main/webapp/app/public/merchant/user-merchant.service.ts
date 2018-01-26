import { UserMerchantResource } from './user-merchant-resource';
import { SERVER_API_URL } from './../../app.constants';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Rx';
import { HttpClient } from '@angular/common/http';

@Injectable()
export class UserMerchantService {
  private ENTPOINT= SERVER_API_URL + '/user-merchants';

  constructor(private http: HttpClient) { }

  public getAll() {
    return this.http.get(this.ENTPOINT).map( (resp: any) => {
      return <UserMerchantResource[]>resp.body;
    }
  ).catch( (error) => {
    console.log(error);
        return Observable.of( false );
    });

    // return this.records;
  }

  public get(id: any) {

    return this.http.get(this.ENTPOINT + '/' + id).map( (resp: any) => {
      return <UserMerchantResource>resp.body;
    }
  ).catch( (error) => {
    console.log(error);
        return Observable.of( false );
    });

  }

  public update(body: any) {
    return this.http.put(this.ENTPOINT, body).map( (resp: any) => {
      return resp;
    }
  ).catch( (error) => {
    console.log(error);
        return Observable.of( false );
    });

  }

  public add(body: any) {
    return this.http.post(this.ENTPOINT, body).map( (resp: any) => {
      return resp;
    }
  ).catch( (error) => {
    console.log(error);
        return Observable.of( false );
    });

  }

  public delete(id: number) {
    return this.http.delete( this.ENTPOINT + '/' + id).map( (resp: any) => {
      return resp;
    }
  ).catch( (error) => {
    console.log(error);
        return Observable.of( false );
    });

  }

}
