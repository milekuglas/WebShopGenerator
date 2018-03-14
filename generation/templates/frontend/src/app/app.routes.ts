import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { StarterComponent } from './starter/starter.component';
import { AuthGuardService } from './auth/shared/auth-guard.service';
import { HomeComponent } from './home/home.component';
{% for product in products %}
import { Single{{ product.name|capitalize }}Component } from './{{ product.name|lower }}/single/single-{{ product.name|lower }}.component';
import { {{ product.name|capitalize }}SearchComponent } from './{{ product.name|lower }}/search/{{ product.name|lower }}-search.component';
{% endfor %}

const routes: Routes = [
  { path: 'login', component: StarterComponent },
  { path: '', component: HomeComponent, canActivate: [ AuthGuardService ] },
  {% for product in products %}
  { path: '{{ product.name|lower }}/:id', component: Single{{ product.name|capitalize }}Component, canActivate: [ AuthGuardService ] },
  { path: '{{ product.name|lower }}', component: {{ product.name|capitalize }}SearchComponent, canActivate: [ AuthGuardService ] },
  {% endfor %}
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes)
  ],
  exports: [
    RouterModule
  ]
})
export class AppRoutingModule { }
