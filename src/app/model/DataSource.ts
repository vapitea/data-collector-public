import {Team} from "./Team";
import {Parameter} from "./Parameter";

export class DataSource {
  id: number;
  uuid: string;
  name: string;
  location: string;
  description: string;
  parameters: Parameter[];
  team: Team;


  constructor(id: number, uuid: string, name: string, location: string, description: string, parameters: Parameter[], team: Team) {
    this.id = id;
    this.uuid = uuid;
    this.name = name;
    this.location = location;
    this.description = description;
    this.parameters = parameters;
    this.team = team;
  }
}
