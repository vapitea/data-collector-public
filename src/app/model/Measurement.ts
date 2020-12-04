import {Parameter} from "./Parameter";

export class Measurement {
  id: number;
  timestamp: Date;
  value: number;
  parameter: Parameter;
}
