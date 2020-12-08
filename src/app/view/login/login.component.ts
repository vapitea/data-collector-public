import {Component, OnInit} from '@angular/core';
import {NgForm} from "@angular/forms";
import {Router} from "@angular/router";
import {HttpService} from "../../service/Http.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(private httpService: HttpService, private router: Router) {
  }

  ngOnInit(): void {
    const username = this.httpService.getUsername();
    const password = this.httpService.getPassword();
    if (username && password) {
      this.httpService.login(username, password);
    }
  }

  onLogin(loginForm: NgForm) {
    this.httpService.login(loginForm.form.value.name, loginForm.form.value.password);
  }
}
