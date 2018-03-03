import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { StarterComponent } from './starter/starter.component';
import { AuthGuardService } from './auth/shared/auth-guard.service';
import { HomeComponent } from './home/home.component';
import { ProfileComponent } from "./profile/profile.component";

const routes: Routes = [
  { path: 'login', component: StarterComponent },
  { path: '', component: HomeComponent, canActivate: [ AuthGuardService ] },
  { path: 'profile', component: ProfileComponent, canActivate: [ AuthGuardService ] }
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
