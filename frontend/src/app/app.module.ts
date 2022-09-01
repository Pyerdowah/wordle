import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { UserService } from './user/user.service';
import { HttpClientModule } from '@angular/common/http';import { FormsModule } from '@angular/forms';
import {WordService} from "./word/word.service";
import {StatisticService} from "./statistic/statistic.service";

@NgModule({
  declarations: [
    AppComponent,
  ],
    imports: [
        BrowserModule, HttpClientModule,
        FormsModule
    ],
  providers: [UserService, WordService, StatisticService],
  bootstrap: [AppComponent]
})
export class AppModule { }
