import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { StarterComponent } from './starter/starter.component';
import { AuthGuardService } from './auth/shared/auth-guard.service';
import { HomeComponent } from './home/home.component';
import { ProfileComponent } from "./profile/profile.component";
{% for product in products %}
import { Single{{ product.name|capitalize }}Component } from './{{ product.name|lower }}/single/single-{{ product.name|lower }}.component';
{% endfor %}

const routes: Routes = [
  { path: 'login', component: StarterComponent },
  { path: '', component: HomeComponent, canActivate: [ AuthGuardService ] },
  { path: 'profile', component: ProfileComponent, canActivate: [ AuthGuardService ] },
  {% for product in products %}
  { path: '{{ product.name|lower }}/:id', component: Single{{ product.name|capitalize }}Component, canActivate: [ AuthGuardService ] },
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
