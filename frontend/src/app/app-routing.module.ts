import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {RouterModule, Routes} from "@angular/router";
import {LoginComponent} from "./login/login.component";
import {RegisterComponent} from "./register/register.component";
import {GameComponent} from "./game/game.component";
import {AuthGuard} from "./auth/auth.guard";



const routes: Routes = [
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'game', component: GameComponent, canActivate: [AuthGuard]},
  {path: '**', redirectTo: 'login', pathMatch: "full"}
]

@NgModule({
  declarations: [],
  imports: [
    CommonModule, RouterModule.forRoot(routes)
  ],
  providers: [LoginComponent],
  exports: [RouterModule]
})
export class AppRoutingModule { }
