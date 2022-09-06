import {Injectable, NgModule} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterOutlet, RouterStateSnapshot, UrlTree} from '@angular/router';
import { Observable } from 'rxjs';
import {AuthguardServiceService} from "./authguard-service.service";
import {User} from "../user/user";
import {AppComponent} from "../app.component";
import {RegisterComponent} from "../register/register.component";
import {LoginComponent} from "../login/login.component";
import {GameComponent} from "../game/game.component";
import {BrowserModule} from "@angular/platform-browser";
import {AppRoutingModule} from "../app-routing.module";
import {FormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import {UserService} from "../user/user.service";

@Injectable({
  providedIn: 'root',
})
export class AuthGuard implements CanActivate {
  constructor(private authGuardService: AuthguardServiceService, private  router: Router) {
  }
  canActivate() : boolean{
    if (!this.authGuardService.getToken()){
      this.router.navigate(['/login']);
    }
    return this.authGuardService.getToken();
  }

}
