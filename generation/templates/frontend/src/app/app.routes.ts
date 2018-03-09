import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { StarterComponent } from './starter/starter.component';
import { AuthGuardService } from './auth/shared/auth-guard.service';
import { HomeComponent } from './home/home.component';

const routes: Routes = [
  { path: 'login', component: StarterComponent },
  { path: '', component: HomeComponent, canActivate: [ AuthGuardService ] }
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
