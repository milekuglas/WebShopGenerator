import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';

import { environment } from '../../../environments/environment';
import {Order} from './order.model';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable()
export class OrderService {

  private orderUrl = 'order';

  constructor(private http: HttpClient) { }

  get(): Observable<[Order]> {
    const url = `${environment.baseUrl}${this.orderUrl}`;
    return this.http.get<[Order]>(url, httpOptions);
  }

  create(order: Order) {
    const url = `${environment.baseUrl}${this.orderUrl}`;
    return this.http.post(url, order).subscribe();
  }
}
