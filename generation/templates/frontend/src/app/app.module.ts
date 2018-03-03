import { NgModule } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatTabsModule } from '@angular/material/tabs';
import { BrowserModule } from '@angular/platform-browser';
import { MatToolbarModule } from '@angular/material/toolbar';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { StarterComponent } from './starter/starter.component';
import { AuthModule } from './auth/auth.module';
import { UserModule } from './user/user.module';
import { AppRoutingModule } from './app.routes';
import { ProfileModule } from "./profile/profile.module";

@NgModule({
    declarations: [
        AppComponent,
        StarterComponent,
        HomeComponent
    ],
    imports: [
        BrowserModule,
        BrowserAnimationsModule,
        MatCardModule,
        MatTabsModule,
        UserModule,
        AuthModule,
        ProfileModule,
        AppRoutingModule,
        MatToolbarModule
    ],
    bootstrap: [AppComponent]
})
export class AppModule {
}

