import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { environment } from '../../../environments/environment';
import { HttpParams } from '@angular/common/http';
import { Product } from './product.model';

@Injectable()
export class ProductService {
private productUrl = 'product';

constructor(private http: HttpClient) {}

  getProducts(): Observable<Product[]> {
    const url = `${environment.baseUrl}${this.productUrl}`;
    return this.http.get<Product[]>(url, {});
  }

  getProductsSearch(searchQuery): Observable<Product[]> {

    let Params = new HttpParams();
    {% for property in product.properties %}
    {% if property.name != 'id' %}
    {% if property.type.name != "Long" and property.type.name != "Int"
        and property.type.name != "Double" and property.type.name != "Float" %}
    if (searchQuery.{{property.name}})
      Params = Params.append('{{property.name}}', searchQuery.{{property.name}});
    {% else %}
    if (searchQuery.{{property.name}}From)
      Params = Params.append('{{property.name}}From', searchQuery.{{property.name}}From);
    if (searchQuery.{{property.name}}To)
      Params = Params.append('{{property.name}}To', searchQuery.{{property.name}}To);
    {% endif %}
    {% endif %}
    {% endfor %}

    const options = { params: Params };
    const url = `${environment.baseUrl}${this.productUrl}/search`;
    return this.http.get<Product[]>(url, options);
  }
}