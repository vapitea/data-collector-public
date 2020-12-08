import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {HeaderComponent} from './view/header/header.component';
import {RouterModule, Routes} from "@angular/router";
import {HomeComponent} from './view/home/home.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {AppRoutingModule} from "./app-routing.module";
import {UserListComponent} from './view/user-list/user-list.component';
import {UserDetailComponent} from './view/user-detail/user-detail.component';
import {HttpService} from "./service/Http.service";
import {HttpClientModule} from "@angular/common/http";
import {LoginComponent} from './view/login/login.component';
import { TeamDetailComponent } from './view/team-detail/team-detail.component';
import { TeamListComponent } from './view/team-list/team-list.component';
import { DataSourceDetailComponent } from './view/data-source-detail/data-source-detail.component';
import { ParameterDetailComponent } from './view/parameter-detail/parameter-detail.component';
import { MeasurementListComponent } from './view/measurement-list/measurement-list.component';


@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    HomeComponent,
    UserListComponent,
    UserDetailComponent,
    LoginComponent,
    TeamDetailComponent,
    TeamListComponent,
    DataSourceDetailComponent,
    ParameterDetailComponent,
    MeasurementListComponent
  ],
  imports: [
    BrowserModule,
    RouterModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    AppRoutingModule,
    NgbModule,
  ],
  providers: [HttpService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
