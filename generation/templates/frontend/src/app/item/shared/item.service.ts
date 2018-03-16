import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs/Observable';
import { HttpParams } from '@angular/common/http';
import { {{product.name|capitalize}} } from './{{product.name|lower}}.model';


@Injectable()
export class {{ product.name|capitalize }}Service {

  private {{ product.name|lower }}URL = '{{ product.name|lower }}';
  private orderItemURL = 'order-item';

  constructor(private http: HttpClient) { }

  get{{ product.name|capitalize }}s(): Observable<{{product.name|capitalize}}[]> {
    const url = `${environment.baseUrl}${this.{{ product.name|lower }}URL}`;
    return this.http.get<{{product.name|capitalize}}[]>(url, {});
  }

  get{{ product.name|capitalize }}(id: number) {
    return this.http.get(`${environment.baseUrl}${this.{{ product.name|lower }}URL}/${id}`);
  }

  addToCart(order: any) {
    return this.http.post(`${environment.baseUrl}${this.orderItemURL}`, order);
  }

  get{{ product.name|capitalize }}sSearch(searchQuery): Observable<{{product.name|capitalize}}[]> {

    let Params = new HttpParams();
    {% for property in product.properties %}
    {% if property.name != 'id' %}
    {% if property.type.name != "Long" and property.type.name != "Int"
        and property.type.name != "Double" and property.type.name != "Float" %}
    if (searchQuery.{{property.name}}){Params = Params.append('{{property.name}}', searchQuery.{{property.name}});}
    {% else %}
    if (searchQuery.{{property.name}}From){Params = Params.append('{{property.name}}From', searchQuery.{{property.name}}From);}
    if (searchQuery.{{property.name}}To){Params = Params.append('{{property.name}}To', searchQuery.{{property.name}}To);}
    {% endif %}
    {% endif %}
    {% endfor %}

    const options = { params: Params };
    const url = `${environment.baseUrl}${this.{{ product.name|lower }}URL}`;
    return this.http.get<{{product.name|capitalize}}[]>(url, options);
  }

}