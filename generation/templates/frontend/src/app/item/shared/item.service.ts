import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';


@Injectable()
export class {{ product.name|capitalize }}Service {

  private {{ product.name|lower }}URL = '{{ product.name|lower }}/';

  constructor(private http: HttpClient) { }

  get{{ product.name|capitalize }}(id: number) {
    return this.http.get(`${environment.baseUrl}${this.{{ product.name|lower }}URL}${id}`);
  }

}