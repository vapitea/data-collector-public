import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {Team} from "../../model/Team";
import {ActivatedRoute, Params, Router} from "@angular/router";
import {HttpService} from "../../service/Http.service";
import {DataSource} from "../../model/DataSource";

@Component({
  selector: 'app-team-detail',
  templateUrl: './team-detail.component.html',
  styleUrls: ['./team-detail.component.css']
})
export class TeamDetailComponent implements OnInit {
  form: FormGroup;
  teamId: any;
  team: Team;

  constructor(public httpService: HttpService, private route: ActivatedRoute, private router: Router) {
    this.team = new Team(null, null, null, [], []);
  }

  ngOnInit(): void {
    this.bindTeam();
    this.initForm()
  }

  onDeleteDataSource(dataSource: DataSource) {
    this.httpService.deleteDataSourceOfTeam(this.teamId, dataSource.id).subscribe(() => {
      this.loadTeamWithDataSources();
    });
  }

  onUpdateOrCreate() {
    const teamInForm: Team = this.form.value;
    if (this.teamId === 'new') {
      this.httpService.createTeam(teamInForm).subscribe((team: Team) => {
        this.router.navigate(['../'], {relativeTo: this.route});
      });
    } else {
      this.httpService.updateTeam(teamInForm).subscribe(() => {
        this.loadTeamWithDataSources();
      });
    }

  }

  onDelete() {
    this.httpService.deleteTeam(this.teamId).subscribe(() => {
      this.router.navigate(['../'], {relativeTo: this.route});
    });
  }

  private bindTeam() {
    this.route.params.subscribe((params: Params) => {
      this.teamId = params['teamId'];

      if (this.teamId !== 'new') {
        this.loadTeamWithDataSources();
      }
    });
  }

  private loadTeamWithDataSources() {
    this.httpService.getTeam(this.teamId).subscribe((teams: Team) => {
      this.team = teams;

      this.httpService.getDataSourcesOfTeam(this.team.id).subscribe((dataSources: DataSource[]) => {
        this.team.dataSources = dataSources;
        this.setForm(this.team);
      });
    });
  }

  private initForm() {
    this.form = new FormGroup({
        id: new FormControl(),
        name: new FormControl(),
        description: new FormControl()
      }
    );
  }

  private setForm(team: Team) {
    this.form.reset({id: team.id, name: team.name, description: team.description});
  }
}
