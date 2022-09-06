import { Component, OnInit } from '@angular/core';
import {UserService} from "../user/user.service";
import {Router} from "@angular/router";
import {NgForm} from "@angular/forms";
import {User} from "../user/user";
import {HttpErrorResponse} from "@angular/common/http";
import {AuthguardServiceService} from "../auth/authguard-service.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  constructor(private userService: UserService, private router: Router, private authService: AuthguardServiceService) { }

  ngOnInit(): void {

  }

  public onLoginUser(loginForm: NgForm): void {
    this.userService.loginUser(loginForm.value).subscribe(
      (response: User) => {
        this.router.navigate(['/game']);
        localStorage.setItem('LOGGED', response.status.toLocaleString());
      },
      (error: HttpErrorResponse) => {
        localStorage.removeItem('LOGGED');
        alert(error.message);
        loginForm.reset();
      }
    );
  }

}
