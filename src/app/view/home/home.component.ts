import {Component, OnDestroy, OnInit} from '@angular/core';
import {HttpService} from "../../service/Http.service";
import {User} from "../../model/User";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit, OnDestroy {
  user: User = new User(null, null, null, null, null);

  constructor(private httpService: HttpService) {
  }

  ngOnInit(): void {
    this.httpService.authenticate().subscribe(user => {
      this.httpService.setUser(user);
      this.user = user
    });


  }

  ngOnDestroy(): void {
  }

}
