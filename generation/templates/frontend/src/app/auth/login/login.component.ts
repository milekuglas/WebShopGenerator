import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';

import { AuthService } from '../shared/auth.service';
import { LoginUser } from '../shared/login-user.model';
import { UserService } from '../../user/shared/user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  signInForm: FormGroup;
  return: string;

  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthService,
    private userService: UserService,
    private snackBar: MatSnackBar,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit() {
    this.signInForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', [Validators.required, Validators.minLength(8)]]
    });
    this.route.queryParams.subscribe(
      params => (this.return = params['return'] || '')
    );
  }

  get username() {
    return this.signInForm.get('username');
  }
  get password() {
    return this.signInForm.get('password');
  }

  onLogin() {
    if (this.signInForm.valid) {
      const user = new LoginUser(this.signInForm.value);
      this.signInForm.reset();
      this.authService.login(user).subscribe(
        u => {
          this.snackBar.open('You are logged in.', 'OK', {
            duration: 2000
          });
          this.router.navigateByUrl(this.return);
        },
        err => {
          this.snackBar.open(err.message, 'Cancel', {
            duration: 2000
          });
        }
      );
    }
  }
}
