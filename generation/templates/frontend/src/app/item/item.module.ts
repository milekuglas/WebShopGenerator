import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { Single{{ product.name|capitalize }}Component } from './single/single-{{ product.name|lower }}.component';
import { {{ product.name|capitalize }}Service } from './shared/{{ product.name|lower }}.service';
import { ShoppingCartModule } from '../shopping-cart/shopping-cart.module';
import { SharedModule } from '../shared/shared.module';


@NgModule({
  declarations: [
    Single{{ product.name|capitalize }}Component
  ],
  providers: [
    {{ product.name|capitalize }}Service
  ],
  imports: [
    SharedModule,
    FormsModule,
    ShoppingCartModule
  ],
  exports: [
    Single{{ product.name|capitalize }}Component
  ]
})
export class {{ product.name|capitalize }}Module { }