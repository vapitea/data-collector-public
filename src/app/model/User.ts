import {Team} from "./Team";

export class User {
  id: number;
  name: string;
  password: string;
  teams: Team[];
  dtype: string;


  constructor(id: number, name: string, password: string, teams: Team[], dtype: string) {
    this.id = id;
    this.name = name;
    this.password = password;
    this.teams = teams;
    this.dtype = dtype;
  }
}
