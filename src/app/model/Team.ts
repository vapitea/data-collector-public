import {User} from "./User";
import {DataSource} from "./DataSource";

export class Team {
  id: number;
  name: string;
  description: string;
  dataSources: DataSource[];
  users: User[];


  constructor(id: number, name: string, description: string, dataSources: DataSource[], users: User[]) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.dataSources = dataSources;
    this.users = users;
  }
}
