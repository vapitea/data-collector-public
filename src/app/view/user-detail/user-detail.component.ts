import {Component, OnInit} from '@angular/core';
import {User} from "../../model/User";
import {Team} from "../../model/Team";
import {FormControl, FormGroup, NgForm} from "@angular/forms";
import {Observable} from "rxjs";
import {debounceTime, distinctUntilChanged, filter, map} from "rxjs/operators";
import {HttpService} from "../../service/Http.service";
import {ActivatedRoute, Params, Router} from "@angular/router";

@Component({
  selector: 'app-user-detail',
  templateUrl: './user-detail.component.html',
  styleUrls: ['./user-detail.component.css']
})
export class UserDetailComponent implements OnInit {
  form: FormGroup;
  userId: any;
  user: User;
  model: Team;
  private teams: Team[] = []

  constructor(private httpService: HttpService, private route: ActivatedRoute, private router: Router) {
    this.user = new User(null, null, null, []);
  }

  ngOnInit(): void {
    this.bindUser();
    this.initForm();
    this.initSearchbar();
  }

  private initForm() {
    this.form = new FormGroup({
      id: new FormControl(),
      name: new FormControl(),
      password: new FormControl()
    })
  }

  private setForm(user: User) {
    this.form.reset({id: user.id, name: user.name, password: user.password});
  }

  onUpdateOrCreate() {
    const userInForm = this.form.value;
    if (this.userId === 'new') {
      this.httpService.createUser(userInForm).subscribe((user: User) => {
        this.router.navigate(['../'], {relativeTo: this.route});
      });
    } else {
      this.httpService.updateUser(userInForm).subscribe(() => {
        this.loadUserWithTeams();
      });
    }
  }

  onDelete() {
    this.httpService.deleteUser(this.user.id).subscribe(() => {
      this.router.navigate(['../'], {relativeTo: this.route});
    });
  }

  onLeave(team: Team) {
    this.httpService.removeUserFromTeam(this.userId, team.id).subscribe(() => {
      this.loadUserWithTeams();
    });
  }

  formatTeamName(team: Team): string {
    if (team) {
      return team.name + '(' + team.id + ')';
    } else {
      return 'Please select a team!';
    }
  }

  formatter = (team: Team) => team.name + '(' + team.id + ')';

  search = (text$: Observable<string>) => text$.pipe(
    debounceTime(200),
    distinctUntilChanged(),
    filter(term => term.length >= 1),
    map(term => this.teams.filter(team => new RegExp(term, 'mi').test(team.name)).slice(0, 16))
  )

  private bindUser() {
    this.route.params.subscribe((params: Params) => {
      this.userId = params['userId'];

      if (this.userId !== 'new') {
        this.loadUserWithTeams();
      }
    });
  }

  private loadUserWithTeams() {
    this.httpService.getUser(this.userId).subscribe((user: User) => {
      user['teams'] = [];
      this.user = user
      this.setForm(this.user);
      this.httpService.getTeamsOfUser(this.userId).subscribe(teams => this.user.teams = teams)
    });
  }

  private initSearchbar() {
    this.httpService.getTeams().subscribe(teams => this.teams = teams);
  }

  onJoinTeam() {
    this.httpService.addUserToTeam(this.userId, this.model).subscribe((user: User) => {
      this.loadUserWithTeams();
      this.model = null;
    });
  }
}
