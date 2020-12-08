import {Injectable} from "@angular/core";
import {User} from "../model/User";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Team} from "../model/Team";
import {ActivatedRoute, Router} from "@angular/router";
import {Observable} from "rxjs";
import {DataSource} from "../model/DataSource";
import {Parameter} from "../model/Parameter";
import {Measurement} from "../model/Measurement";

@Injectable()
export class HttpService {
  private baseURL: string = 'http://localhost:8080/api/v1.0'
  private loggedInUser: User;
  private username: string;
  private password: string;
  private headers;

  constructor(public http: HttpClient, private router: Router, private route: ActivatedRoute) {
    this.loggedInUser = new User(null, null, null, null, null);
    this.loadCredentials();
  }


  getUsers() {
    return this.http.get<User[]>(this.baseURL + '/users', {headers: this.headers});
  }

  getUser(id: number) {
    return this.http.get<User>(this.baseURL + '/users/' + id, {headers: this.headers});
  }


  getDataSource(id: number) {
    return this.http.get<DataSource>(this.baseURL + '/dataSources/' + id, {headers: this.headers});
  }

  getParameter(id: number) {
    return this.http.get<Parameter>(this.baseURL + '/parameters/' + id, {headers: this.headers});
  }

  getTeamsOfUser(id: number) {
    return this.http.get<Team[]>(this.baseURL + '/users/' + id + '/teams', {headers: this.headers});
  }

  getParametersOfDataSource(id: number) {
    return this.http.get<Parameter[]>(this.baseURL + '/dataSources/' + id + '/parameters', {headers: this.headers});
  }

  getTeams() {
    return this.http.get<Team[]>(this.baseURL + '/teams', {headers: this.headers});
  }

  updateUser(user: User) {
    return this.http.put<User>(this.baseURL + '/users/' + user.id, user, {headers: this.headers});
  }

  updateTeam(team: Team) {
    return this.http.put<Team>(this.baseURL + '/teams/' + team.id, team, {headers: this.headers});
  }

  updateDataSource(dataSource: DataSource) {
    return this.http.put<DataSource>(this.baseURL + '/dataSources/' + dataSource.id, dataSource, {headers: this.headers});
  }

  updateDataParameter(parameter: Parameter) {
    return this.http.put<Parameter>(this.baseURL + '/parameters/' + parameter.id, parameter, {headers: this.headers});
  }

  deleteUser(id: number) {
    return this.http.delete(this.baseURL + '/users/' + id, {headers: this.headers});
  }

  deleteTeam(id: number) {
    return this.http.delete(this.baseURL + '/teams/' + id, {headers: this.headers});
  }


  deleteDataSourceOfTeam(teamId: number, dataSourceId: number) {
    return this.http.delete(this.baseURL + '/teams/' + teamId + '/dataSources/' + dataSourceId, {headers: this.headers});
  }

  deleteParameterOfDataSource(dataSourceId: number, parameterId: number) {
    return this.http.delete(this.baseURL + '/dataSources/' + dataSourceId + '/parameters/' + parameterId, {headers: this.headers});
  }

  deleteDataSource(id: number) {
    return this.http.delete(this.baseURL + '/dataSources/' + id, {headers: this.headers});
  }

  deleteParameter(id: number) {
    return this.http.delete(this.baseURL + '/parameters/' + id, {headers: this.headers});
  }

  createUser(user: User) {
    return this.http.post<User>(this.baseURL + '/users/', user, {headers: this.headers});
  }

  createTeam(team: Team) {
    return this.http.post<Team>(this.baseURL + '/teams/', team, {headers: this.headers});
  }

  removeUserFromTeam(userId: number, teamId: number) {
    return this.http.delete(this.baseURL + '/users/' + userId + '/teams/' + teamId, {headers: this.headers});
  }

  addUserToTeam(userId: number, teamId: number) {
    return this.http.post<User>(this.baseURL + '/users/' + userId + '/teams/' + teamId, null, {headers: this.headers})
  }

  createDataSourceOfTeam(teamId: number, dataSource: DataSource) {
    return this.http.post<DataSource>(this.baseURL + '/teams/' + teamId + '/dataSources/', dataSource, {headers: this.headers})
  }


  createParameterOfDataSource(dataSourceId: number, parameter: Parameter) {
    return this.http.post<Parameter>(this.baseURL + '/dataSources/' + dataSourceId + '/parameters/', parameter, {headers: this.headers})
  }

  getTeam(id: number) {
    return this.http.get<Team>(this.baseURL + '/teams/' + id, {headers: this.headers});
  }

  getDataSourcesOfTeam(id: number) {
    return this.http.get<DataSource[]>(this.baseURL + '/teams/' + id + '/dataSources', {headers: this.headers});
  }

  getMeasurementsOfParameter(id: number) {
    return this.http.get<Measurement[]>(this.baseURL + '/parameters/' + id + '/measurements', {headers: this.headers});
  }


  public authenticate(): Observable<User> {

    const headers = new HttpHeaders({
      'Authorization': 'Basic ' + btoa(this.username + ':' + this.password)
    });

    this.headers = headers;

    if (this.loggedInUser) {
      if (this.loggedInUser.id != null) {
        return new Observable<User>((observer => {
          observer.next(this.loggedInUser);
          observer.complete();
        }));
      }
    }

    if (this.username && this.password) {
      return this.http.get<User>(this.baseURL + '/ownUser', {headers: headers});
    } else {
      return new Observable<User>((observer => {
        //observer.error();
        observer.complete();
      }))
    }
  }


  logout() {
    this.loggedInUser = null;
    this.username = null;
    this.password = null;
    this.headers = null;
    localStorage.removeItem("username");
    localStorage.removeItem("password");
    this.router.navigate(['/login']);
  }

  getRole() {
    return this.loggedInUser.dtype;
  }


  isLoggedIn(): boolean {
    return this.loggedInUser.id != null;
  }


  getLoggedInUser(): User {
    return this.loggedInUser;
  }


  setUser(user: User) {
    this.loggedInUser = user;
    this.saveCredentials()
  }

  login(name, password) {
    this.password = password;
    this.username = name;
    this.authenticate().subscribe(user => {
      this.setUser(user);
      this.router.navigate(['/']);
    });
  }

  getUsername() {
    return this.username;
  }

  getPassword() {
    return this.password;
  }

  private saveCredentials() {
    if (this.username && this.password) {
      localStorage.setItem('username', this.username);
      localStorage.setItem('password', this.password);
    }
  }

  private loadCredentials() {
    const username = localStorage.getItem("username");
    const password = localStorage.getItem("password");
    this.username = username;
    this.password = password;
    if (username && password) {
      this.authenticate().subscribe(user => {
        this.setUser(user);
      });
    }

  }
}
