import {User} from "./User";
import {DataSource} from "./DataSource";

export class Team {
  id: number;
  name: string;
  dataSources: DataSource[];
  users: User[];


  constructor(id: number, name: string, dataSources: DataSource[], users: User[]) {
    this.id = id;
    this.name = name;
    this.dataSources = dataSources;
    this.users = users;
  }
}
