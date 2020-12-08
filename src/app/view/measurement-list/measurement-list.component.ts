import {Component, OnInit} from '@angular/core';
import {Measurement} from "../../model/Measurement";
import {HttpService} from "../../service/Http.service";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-measurement-list',
  templateUrl: './measurement-list.component.html',
  styleUrls: ['./measurement-list.component.css']
})
export class MeasurementListComponent implements OnInit {
  measurements: Measurement[] = [];

  constructor(private http: HttpService, private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.bindMeasurements();
  }

  private bindMeasurements() {
    const parameterId = +this.route.snapshot.params['parameterId'];
    this.http.getMeasurementsOfParameter(parameterId).subscribe((measurements: Measurement[]) => {
      this.measurements = measurements;
    });

  }
}
