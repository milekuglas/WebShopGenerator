import 'rxjs/add/observable/throw';

import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { JwtHelper } from 'angular2-jwt';
import { Observable } from 'rxjs/Observable';
import { of } from 'rxjs/observable/of';
import { catchError, tap } from 'rxjs/operators';

import { environment } from '../../../environments/environment';
import { LoggedInUser } from './logged-in-user.model';
import { LoginUser } from './login-user.model';
import { User } from './../../user/shared/user.model';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable()
export class AuthService {

  private loginUrl = 'auth/login';
  private refreshUrl = 'auth/refresh';
  private jwtHelper: JwtHelper;

  user: User;

  constructor(private http: HttpClient, private router: Router) {
    this.jwtHelper = new JwtHelper();
    const accessToken = localStorage.getItem('access_token');
    if (accessToken && localStorage.getItem('refresh_token')) {
      this.user = this.jwtHelper.decodeToken(accessToken);
    }
  }

  login(user: LoginUser): Observable<LoggedInUser> {
    const url = environment.baseUrl + this.loginUrl;
    return this.http.post<LoggedInUser>(url, user, httpOptions).pipe(
      tap(u => this.saveTokens(u)),
      catchError(err => Observable.throw(new Error(err.error)))
    );
  }

  refreshToken(): Observable<LoggedInUser> {
    if (this.user) {
      const url = `${environment.baseUrl}${this.refreshUrl}`;
      const token = localStorage.getItem('refresh_token');
      return this.http.post<LoggedInUser>(url, { token: token }, httpOptions).pipe(
        tap(user => this.saveTokens(user)),
        catchError(err => Observable.throw(new Error(err.error)))
      );
    } else {
      return this.logout();
    }
  }

  logout() {
    this.user = null;
    localStorage.removeItem('access_token');
    localStorage.removeItem('refresh_token');
    this.router.navigate(['/login']);
    return of(null);
  }

  private saveTokens(user: LoggedInUser) {
    this.user = this.jwtHelper.decodeToken(user.accessToken);
    localStorage.setItem('access_token', user.accessToken);
    localStorage.setItem('refresh_token', user.refreshToken);
  }
}
