import { Component, OnInit } from '@angular/core';
import {NgForm} from "@angular/forms";
import {UserService} from "../user/user.service";
import {HttpErrorResponse} from "@angular/common/http";
import {User} from "../user/user";
import {Router} from "@angular/router";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  constructor(private userService: UserService, private router: Router) { }

  ngOnInit(): void {
  }

  public onRegisterUser(addForm: NgForm): void {
    this.userService.registerNewUser(addForm.value).subscribe(
      (response: User) => {
          this.router.navigate(['/login']);
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
        addForm.reset();
      }
    );
  }

}
