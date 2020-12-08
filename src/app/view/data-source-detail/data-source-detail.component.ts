import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {DataSource} from "../../model/DataSource";
import {ActivatedRoute, Params, Router} from "@angular/router";
import {HttpService} from "../../service/Http.service";
import {Parameter} from "../../model/Parameter";

@Component({
  selector: 'app-data-source-detail',
  templateUrl: './data-source-detail.component.html',
  styleUrls: ['./data-source-detail.component.css']
})
export class DataSourceDetailComponent implements OnInit {
  form: FormGroup;
  dataSourceId: any;
  dataSource: DataSource;

  constructor(public httpService: HttpService, private route: ActivatedRoute, private router: Router) {
    this.dataSource = new DataSource(null, null, null, null, null, [], null);
  }

  ngOnInit(): void {
    this.bindDataSource();
    this.initForm();
  }

  onUpdateOrCreate() {
    const dataSourceInForm: DataSource = this.form.value;
    const teamId = this.route.snapshot.params['teamId'];
    if (this.dataSourceId === 'new') {
      this.httpService.createDataSourceOfTeam(teamId,dataSourceInForm).subscribe(() => {
        this.router.navigate(['../../'], {relativeTo: this.route});
      });
    } else {
      this.httpService.updateDataSource(dataSourceInForm).subscribe(() => {
        this.loadDataSourceWithParameters();
      });
    }
  }

  onDelete() {
    this.httpService.deleteDataSource(this.dataSourceId).subscribe(() => {
      this.router.navigate(['../../'], {relativeTo: this.route});
    });
  }

  private bindDataSource() {
    this.route.params.subscribe((params: Params) => {
      this.dataSourceId = params['dataSourceId'];

      if (this.dataSourceId !== 'new') {
        this.loadDataSourceWithParameters();
      }
    });
  }

  private loadDataSourceWithParameters() {
    this.httpService.getDataSource(this.dataSourceId).subscribe((dataSource: DataSource) => {
      this.dataSource = dataSource;

      this.httpService.getParametersOfDataSource(this.dataSource.id).subscribe((parameters: Parameter[]) => {
        this.dataSource.parameters = parameters;
        this.setForm(this.dataSource);
      });
    });
  }

  private initForm() {
    this.form = new FormGroup({
        id: new FormControl(),
        uuid: new FormControl(),
        name: new FormControl(),
        location: new FormControl(),
        description: new FormControl(),
      }
    );
  }

  private setForm(dataSource: DataSource) {
    this.form.patchValue(dataSource);
  }

  onDeleteParameter(parameter: Parameter) {
    this.httpService.deleteParameterOfDataSource(this.dataSourceId, parameter.id).subscribe(() => {
      this.loadDataSourceWithParameters();
    });
  }
}
