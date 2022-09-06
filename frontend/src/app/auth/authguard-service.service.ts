import {Inject, Injectable, NgModule, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";



@Injectable({
  providedIn: 'root'
})
export class AuthguardServiceService{

  constructor(private route: ActivatedRoute) {

  }

  public getToken(){
    if (localStorage.getItem('LOGGED') != 'LOGGED'){
      localStorage.removeItem('LOGGED');
    }
    return !!localStorage.getItem('LOGGED');
  }





}
