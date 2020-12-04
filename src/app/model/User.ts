import {Team} from "./Team";

export class User {
  id: number;
  name: string;
  password: string;
  teams: Team[];


  constructor(id: number, name: string, password: string, teams: Team[]) {
    this.id = id;
    this.name = name;
    this.password = password;
    this.teams = teams;
  }
}
