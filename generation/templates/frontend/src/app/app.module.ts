import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { StarterComponent } from './starter/starter.component';
import { AuthModule } from './auth/auth.module';
import { UserModule } from './user/user.module';
import { AppRoutingModule } from './app.routes';
import { SharedModule } from './shared/shared.module';
import { ProfileModule } from "./profile/profile.module";
import { ProductModule } from './product/product.module';
{% for product in products %}
import { {{ product.name|capitalize }}Module } from './{{ product.name|lower }}/{{ product.name|lower }}.module';
{% endfor %}
import { CategoryService } from './category/shared/category.service';
import { RouterModule } from '@angular/router';

@NgModule({
  declarations: [
    AppComponent,
    StarterComponent,
    HomeComponent
  ],
  imports: [
    BrowserModule,
    RouterModule,
    SharedModule,
    UserModule,
    AuthModule,
    AppRoutingModule,
    ProfileModule,
    ProductModule,
    {% for product in products %}
    {{ product.name|capitalize }}Module,
    {% endfor %}
  ],
  providers: [ CategoryService ],
  bootstrap: [ AppComponent ]
})
export class AppModule {
}

