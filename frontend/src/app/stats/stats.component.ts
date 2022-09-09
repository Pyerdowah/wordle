import { Component, OnInit } from '@angular/core';
import {UserService} from "../user/user.service";
import {StatisticService} from "../statistic/statistic.service";
import {WordService} from "../word/word.service";
import {resolve} from "@angular/compiler-cli";
import {User} from "../user/user";
import {
  Chart,
  ChartConfiguration,
  LineController,
  LineElement,
  PointElement,
  LinearScale,
  Title,
  BarElement, BarController, CategoryScale
} from 'chart.js'

@Component({
  selector: 'app-stats',
  templateUrl: './stats.component.html',
  styleUrls: ['./stats.component.css']
})
export class StatsComponent implements OnInit {
  arrayOfWords: string[] = []
  arrayOfWordsId: number[] = []
  xAxis: number[] = []
  myChart: any
  numberOfTries: any
  userId: any
  wordId: any
  wordName: any
  constructor(private userService: UserService, private statisticService: StatisticService, private wordService: WordService) { }

  ngOnInit(): void {
    this.getWordList();
  }

  public getWordList(){
    this.userService.getUserByLogin(localStorage.getItem('currentUser') as string).subscribe(
      (response: User) => {
        this.statisticService.getUsersWords(response.userId).subscribe(
          (response) => {
            this.arrayOfWordsId = response
            for (var i = 0; i < response.length; i++) {
              this.wordService.getWordById(response[i]).subscribe(
                (response) => {
                  this.arrayOfWords.push(response.wordName)
                  this.arrayOfWords.sort()
                }
              )
            }
          }

        )
      }
    )
  }

  public getNumberOfTries(word: string){
    this.userService.getUserByLogin(localStorage.getItem('currentUser') as string).subscribe(
      (responseU: User) => {
        this.statisticService.getUsersWords(responseU.userId).subscribe(
          (response) => {
            this.arrayOfWordsId = response
            for (var i = 0; i < response.length; i++) {
              this.wordService.getWordById(response[i]).subscribe(
                (response) => {
                  if (response.wordName == word) {
                    this.statisticService.getStatisticByLoginAndWord(response.wordId, responseU.userId).subscribe(
                      (statistic) => {
                        this.numberOfTries = statistic.numberOfTries;
                        this.userId = statistic.user.userId
                        this.wordId = statistic.correctWord.wordId
                        this.statisticService.getStatistic(this.wordId).subscribe(
                          (response) => {
                            for (var j = 0; j < response.length; j++) {
                              this.xAxis.pop()
                            }
                            for (var j = 0; j < response.length; j++) {
                              this.xAxis.push(response[j]);
                            }
                            this.showPlot()
                          }
                        )
                      }
                    )
                  }
                }
              )
            }
          }

        )
      }
    )
  }

  public colorBars(): string[] {
    var colorArray = []
    for (var i = 1; i <= 7; i++) {
      if (i != this.numberOfTries) {
        colorArray.push('rgba(255, 99, 132, 0.2)')
      }
      else {
        colorArray.push('#886f68')
      }
    }
    return colorArray
  }

  public getStatisticData(word: string){
    if (this.myChart != null) {
      this.myChart.destroy();
    }
    this.getNumberOfTries(word)
  }

  public showPlot() {
    const ctx = <HTMLCanvasElement> document.getElementById('myChart');
    ctx.getContext('2d')
    Chart.register(BarController, CategoryScale, LinearScale, BarElement);
    this.myChart = new Chart(ctx, {
      type: 'bar',
      data: {
        labels: ['1', '2', '3', '4', '5', '6', 'lost:c'],
        datasets: [{
          data: this.xAxis,
          backgroundColor: this.colorBars()
        }]
      }
    })
  }

}
