import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';

import { environment } from '../../../environments/environment';
import {OrderItem} from "./order-item.model";

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable()
export class OrderItemService {

  constructor(private http: HttpClient) { }

  get(orderId: number): Observable<[OrderItem]> {
    const url = `${environment.baseUrl}order/${orderId}/order-item`;
    return this.http.get<[OrderItem]>(url, httpOptions);
  }

  getShoppingCartItems(): Observable<[OrderItem]> {
    const url = `${environment.baseUrl}order-item/shopping-cart`;
    return this.http.get<[OrderItem]>(url, httpOptions);
  }
}
