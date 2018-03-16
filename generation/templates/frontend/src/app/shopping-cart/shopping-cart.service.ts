import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';

@Injectable()
export class ShoppingCartService {

  private scURL = 'shopping-cart';

  constructor(private http: HttpClient) { }

  getCart(id: number) {
    return this.http.get(`${environment.baseUrl}${this.scURL}`);
  }

}
