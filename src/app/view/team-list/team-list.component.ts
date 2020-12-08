import {Component, OnInit} from '@angular/core';
import {HttpService} from "../../service/Http.service";
import {Team} from "../../model/Team";

@Component({
  selector: 'app-team-list',
  templateUrl: './team-list.component.html',
  styleUrls: ['./team-list.component.css']
})
export class TeamListComponent implements OnInit {
  teams: Team[] = [];

  constructor(private http: HttpService) {
  }

  ngOnInit(): void {
    this.http.getTeams().subscribe((teams: Team[]) => {
      this.teams = teams;
    });
  }

}
