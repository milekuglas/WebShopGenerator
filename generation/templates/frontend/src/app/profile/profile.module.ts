import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatCardModule } from '@angular/material/card';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { ProfileComponent } from './profile.component';
import { UserInfoComponent } from './user-info/user-info.component';
import {ShoppingCartComponent} from './shopping-cart/shopping-cart.component';
import {ShoppingHistoryComponent} from './shopping-history/shopping-history.component';

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
  declarations: [ UserInfoComponent, ProfileComponent, ShoppingCartComponent, ShoppingHistoryComponent ],
  providers: [ ],
  exports: [ UserInfoComponent, ProfileComponent, ShoppingCartComponent, ShoppingHistoryComponent ],
  schemas: [ CUSTOM_ELEMENTS_SCHEMA ]
})
export class ProfileModule { }
