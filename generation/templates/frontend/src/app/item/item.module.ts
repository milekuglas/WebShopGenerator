import { NgModule } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';

import { Single{{ product.name|capitalize }}Component } from './single/single-{{ product.name|lower }}.component';
import { {{ product.name|capitalize }}Service } from './shared/{{ product.name|lower }}.service';


@NgModule({
  declarations: [
    Single{{ product.name|capitalize }}Component
  ],
  providers: [
    {{ product.name|capitalize }}Service
  ],
  imports: [
    MatButtonModule
  ],
  exports: [
    Single{{ product.name|capitalize }}Component
  ]
})
export class {{ product.name|capitalize }}Module { }