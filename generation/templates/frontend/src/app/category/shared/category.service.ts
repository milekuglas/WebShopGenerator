import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs/Observable';
import { HttpParams } from '@angular/common/http';
import { Category } from './category.model';

@Injectable()
export class CategoryService {
private categoryURL = 'category';

constructor(private http: HttpClient) {}

  getCategories(): Observable<Category[]> {
    const url = `${environment.baseUrl}${this.categoryURL}`;
    return this.http.get<Category[]>(url, {});
  }

  getCategory(id: number) {
    const url = `${environment.baseUrl}${this.categoryURL}/${id}`;
    return this.http.get<Category>(url);
  }
}
