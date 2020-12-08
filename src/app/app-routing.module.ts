import {Route, RouterModule} from "@angular/router";
import {HomeComponent} from "./view/home/home.component";
import {NgModule} from "@angular/core";
import {UserListComponent} from "./view/user-list/user-list.component";
import {UserDetailComponent} from "./view/user-detail/user-detail.component";
import {LoginComponent} from "./view/login/login.component";
import {AdminGuard, canActivateHome, GenericAuthGuard} from "./service/AuthGuard.service";
import {TeamDetailComponent} from "./view/team-detail/team-detail.component";
import {TeamListComponent} from "./view/team-list/team-list.component";
import {DataSourceDetailComponent} from "./view/data-source-detail/data-source-detail.component";
import {ParameterDetailComponent} from "./view/parameter-detail/parameter-detail.component";
import {MeasurementListComponent} from "./view/measurement-list/measurement-list.component";

const appRoutes: Route[] = [
  {path: '', component: HomeComponent, canActivate: [canActivateHome]},
  {path: 'login', component: LoginComponent},
  {path: 'users', component: UserListComponent, canActivate: [AdminGuard]},
  {path: 'users/:userId', component: UserDetailComponent, canActivate: [GenericAuthGuard]},
  {path: 'teams', component: TeamListComponent, canActivate: [AdminGuard]},
  {path: 'teams/:teamId', component: TeamDetailComponent, canActivate: [GenericAuthGuard]},
  {
    path: 'teams/:teamId/dataSources/:dataSourceId',
    component: DataSourceDetailComponent,
    canActivate: [GenericAuthGuard]
  },
  {
    path: 'teams/:teamId/dataSources/:dataSourceId/parameters/:parameterId',
    component: ParameterDetailComponent,
    canActivate: [GenericAuthGuard]
  },
  {
    path: 'teams/:teamId/dataSources/:dataSourceId/parameters/:parameterId/measurements',
    component: MeasurementListComponent,
    canActivate: [GenericAuthGuard]
  }


];

@NgModule({
  imports: [RouterModule.forRoot(appRoutes)],
  exports: [RouterModule],
  providers: [canActivateHome, GenericAuthGuard, AdminGuard]
})

export class AppRoutingModule {

}
