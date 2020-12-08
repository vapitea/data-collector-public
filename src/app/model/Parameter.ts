import {DataSource} from "./DataSource";
import {Measurement} from "./Measurement";

export class Parameter {
  id: number;
  name: string;
  unit: string;
  description: string;
  dataSource: DataSource;
  measurements: Measurement[];


  constructor(id: number, name: string, unit: string, description: string, dataSource: DataSource, measurements: Measurement[]) {
    this.id = id;
    this.name = name;
    this.unit = unit;
    this.description = description;
    this.dataSource = dataSource;
    this.measurements = measurements;
  }
}
