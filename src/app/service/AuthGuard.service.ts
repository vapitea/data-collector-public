import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from "@angular/router";
import {Observable} from "rxjs";
import {HttpService} from "./Http.service";
import {Injectable} from "@angular/core";
import {map} from "rxjs/operators";

@Injectable()
export class GenericAuthGuard implements CanActivate {

  constructor(private httpService: HttpService, private router: Router) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    return this.httpService.authenticate().pipe(
      map(user => {
        this.httpService.setUser(user);
        if (user.id == null) {
          return false;
        }
        return true;
      })
    );
  }
}

@Injectable()
export class AdminGuard implements CanActivate {

  constructor(private httpService: HttpService, private router: Router) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    return this.httpService.authenticate().pipe(
      map(user => {
        this.httpService.setUser(user);
        if (user.id == null || user.dtype !== 'Admin') {
          return false;
        }
        return true;
      })
    );
  }
}


@Injectable()
export class canActivateHome implements CanActivate {


  constructor(private httpService: HttpService, private router: Router) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {

    if (!this.httpService.isLoggedIn()) {
      this.router.navigate(['/login']);
      return false;
    }
    return true;
  }

}
