import {Component, OnInit} from '@angular/core';
import {User} from "../../model/User";
import {HttpService} from "../../service/Http.service";

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {
  users: User[] = []

  constructor(private httpService: HttpService) {
  }

  ngOnInit(): void {
    this.httpService.getUsers().subscribe((users: User[]) => {
      this.users = users;
    });
  }

}
