import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {ActivatedRoute, Params, Router} from "@angular/router";
import {HttpService} from "../../service/Http.service";
import {Parameter} from "../../model/Parameter";
import {DataSource} from "../../model/DataSource";

@Component({
  selector: 'app-parameter-detail',
  templateUrl: './parameter-detail.component.html',
  styleUrls: ['./parameter-detail.component.css']
})
export class ParameterDetailComponent implements OnInit {
  form: FormGroup;
  parameterId: any;
  parameter: Parameter;

  constructor(public httpService: HttpService, private route: ActivatedRoute, private router: Router) {
  }

  ngOnInit(): void {
    this.bindParameter();
    this.initForm();
  }

  onUpdateOrCreate() {
    const parameterInForm: Parameter = this.form.value;
    const dataSourceId = this.route.snapshot.params['dataSourceId'];
    if (this.parameterId === 'new') {
      this.httpService.createParameterOfDataSource(dataSourceId,parameterInForm).subscribe(() => {
        this.router.navigate(['../../'], {relativeTo: this.route});
      });
    } else {
      this.httpService.updateDataParameter(parameterInForm).subscribe(() => {
        this.loadParameter();
      });
    }
  }

  onDelete() {
    this.httpService.deleteParameter(this.parameterId).subscribe(() => {
      this.router.navigate(['../../'], {relativeTo: this.route});
    });
  }

  private bindParameter() {
    this.route.params.subscribe((params: Params) => {
      this.parameterId = params['parameterId'];

      if (this.parameterId !== 'new') {
        this.loadParameter();
      }
    });
  }

  private initForm() {
    this.form = new FormGroup({
      id: new FormControl(),
      name: new FormControl(),
      unit: new FormControl(),
      description: new FormControl(),
    })
  }

  private loadParameter() {
    this.httpService.getParameter(this.parameterId).subscribe((parameter: Parameter) => {
      this.parameter = parameter;
      this.setForm(this.parameter);

    });
  }

  private setForm(parameter: Parameter) {
    this.form.reset(parameter);
  }
}
