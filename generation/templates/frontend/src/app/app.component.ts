import { Component } from '@angular/core';

import { AuthService } from './auth/shared/auth.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  constructor(private authService: AuthService) {}

  user() { return this.authService.user; }

  logout() {
    this.authService.logout();
  }

}
