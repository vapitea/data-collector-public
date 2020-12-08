import {Component, OnDestroy, OnInit} from '@angular/core';
import {HttpService} from "../../service/Http.service";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit, OnDestroy {
  isMenuCollapsed = true;



  constructor(public httpService: HttpService) {
  }

  ngOnInit(): void {
  }

  onLogout() {
    this.httpService.logout();
  }

  ngOnDestroy(): void {
  }
}
