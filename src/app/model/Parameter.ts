import {DataSource} from "./DataSource";
import {Measurement} from "./Measurement";

export class Parameter {
  id: number;
  name: string;
  unit: string;
  description: string;
  dataSource: DataSource;
  measurements: Measurement[];
}
