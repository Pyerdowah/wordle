import { Component, OnInit } from '@angular/core';
import { User } from './user/user';
import { UserService } from './user/user.service';
import {HttpErrorResponse, HttpStatusCode} from '@angular/common/http';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  constructor(private userService: UserService) {
  }

  ngOnInit() {

  }

  public onRegisterUser(registerForm: NgForm): void {
    document.getElementById('register-user-form').click();
    this.userService.registerNewUser(registerForm.value).subscribe(
      (response: User) => {registerForm.reset()},
      (error: HttpErrorResponse) => {alert(error.message);
      registerForm.reset()
      }
    );
  }
}
