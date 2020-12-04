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
}
