import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { catchError } from 'rxjs/operators';

import { environment } from '../../../environments/environment';
import { User } from './user.model';
import { RegistrationUser } from './registration-user.model';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable()
export class UserService {

  private userUrl = 'user';

  constructor(private http: HttpClient) { }

  register(user: RegistrationUser): Observable<User> {
    const url = `${environment.baseUrl}${this.userUrl}`;
    return this.http.post<User>(url, user, httpOptions).pipe(
      catchError(err => {
        return Observable.throw(new Error(err.error));
      })
    );
  }

  getUserData(): Observable<User> {
    const url = `${environment.baseUrl}${this.userUrl}/me`;
    return this.http.get<User>(url, httpOptions);
  }
}