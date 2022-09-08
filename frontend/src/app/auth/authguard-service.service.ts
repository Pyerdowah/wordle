import {Inject, Injectable, NgModule, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {UserService} from "../user/user.service";
import {User} from "../user/user";



@Injectable({
  providedIn: 'root'
})
export class AuthguardServiceService{
  constructor() {

  }

  public getToken(){
    return !!localStorage.getItem('currentUser');
  }





}
