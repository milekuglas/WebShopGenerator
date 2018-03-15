import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { Single{{ product.name|capitalize }}Component } from './single/single-{{ product.name|lower }}.component';
import { {{ product.name|capitalize }}SearchComponent } from './search/{{ product.name|lower }}-search.component';
import { {{ product.name|capitalize }}Service } from './shared/{{ product.name|lower }}.service';
import { ShoppingCartModule } from '../shopping-cart/shopping-cart.module';
import { SharedModule } from '../shared/shared.module';


@NgModule({
  declarations: [
    Single{{ product.name|capitalize }}Component,
    {{ product.name|capitalize }}SearchComponent
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
    Single{{ product.name|capitalize }}Component,
    {{ product.name|capitalize }}SearchComponent
  ]
})
export class {{ product.name|capitalize }}Module { }