import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { StarterComponent } from './starter/starter.component';
import { AuthModule } from './auth/auth.module';
import { UserModule } from './user/user.module';
import { AppRoutingModule } from './app.routes';
import { SharedModule } from './shared/shared.module';
import { ProductModule } from './product/product.module';

@NgModule({
  declarations: [
    AppComponent,
    StarterComponent,
    HomeComponent
  ],
  imports: [
    BrowserModule,
    SharedModule,
    UserModule,
    AuthModule,
    AppRoutingModule,
    ProductModule
  ],
  bootstrap: [ AppComponent ]
})
export class AppModule { }
