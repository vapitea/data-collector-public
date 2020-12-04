import {Route, RouterModule} from "@angular/router";
import {HomeComponent} from "./view/home/home.component";
import {NgModel} from "@angular/forms";
import {NgModule} from "@angular/core";
import {UserListComponent} from "./view/user-list/user-list.component";
import {UserDetailComponent} from "./view/user-detail/user-detail.component";

const appRoutes: Route[] = [
  {path: '', component: HomeComponent},
  {path: 'users/:userId', component: UserDetailComponent},
  {path: 'users', component: UserListComponent}

];

@NgModule({
  imports: [RouterModule.forRoot(appRoutes)],
  exports: [RouterModule]
})

export class AppRoutingModule {

}
