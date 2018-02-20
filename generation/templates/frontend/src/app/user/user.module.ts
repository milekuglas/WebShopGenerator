import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatCardModule } from '@angular/material/card';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';

import { RegistrationComponent } from './registration/registration.component';
import { UserService } from './shared/user.service';

@NgModule({
  imports: [
    CommonModule,
    HttpClientModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatCardModule,
    MatGridListModule,
    MatInputModule,
    MatButtonModule
  ],
  declarations: [ RegistrationComponent ],
  providers: [ UserService ],
  exports: [ RegistrationComponent ]
})
export class UserModule { }
