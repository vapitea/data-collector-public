import {Injectable} from "@angular/core";
import {User} from "../model/User";
import {Observable, Subject} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {Team} from "../model/Team";

@Injectable()
export class HttpService {
  private baseURL: string = 'http://localhost:8080/api/v1.0'


  constructor(private http: HttpClient) {
  }

  getUsers() {
    return this.http.get<User[]>(this.baseURL + '/users');
  }

  getUser(id: number) {
    return this.http.get<User>(this.baseURL + '/users/' + id);
  }


  getTeamsOfUser(id: number) {
    return this.http.get<Team[]>(this.baseURL + '/users/' + id + '/teams');
  }

  getTeams() {
    return this.http.get<Team[]>(this.baseURL + '/teams');
  }

  updateUser(user: User) {
    return this.http.put<User>(this.baseURL + '/users/' + user.id, user);
  }

  deleteUser(id: number) {
    return this.http.delete(this.baseURL + '/users/' + id);
  }

  createUser(user: User) {
    return this.http.post<User>(this.baseURL + '/users/', user);
  }

  removeUserFromTeam(userId: number, teamId: number) {
    return this.http.delete(this.baseURL + '/users/' + userId + '/teams/' + teamId);
  }

  addUserToTeam(userId: number, team: Team) {
    return this.http.post<User>(this.baseURL + '/users/' + userId + '/teams/', team)
  }
}
