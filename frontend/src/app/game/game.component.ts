import {Component, ElementRef, OnInit} from '@angular/core';
import {
  FormArray,
  FormControl,
  FormGroup,
} from "@angular/forms";
import {HttpErrorResponse} from "@angular/common/http";
import {WordService} from "../word/word.service";
import {Word} from "../word/word";
import Keyboard from 'simple-keyboard'
import {identity} from "rxjs";
import {StatisticService} from "../statistic/statistic.service";
import {Statistic} from "../statistic/statistic";
import {UserService} from "../user/user.service";
import {User} from "../user/user";

@Component({
  selector: 'app-game',
  templateUrl: './game.component.html',
  styleUrls: ['./game.component.css'],
})
export class GameComponent implements OnInit {
  randomWord: any
  wordId: any
  wordName: any
  validWord: any
  won: any
  lost: any
  id: any
  letters:string[] = []
  element: any
  newGame: any
  inputId: any
  forms = new FormArray([new FormGroup({
    id0: new FormControl(''),
    id1: new FormControl(''),
    id2: new FormControl(''),
    id3: new FormControl(''),
    id4: new FormControl('')
  }), new FormGroup({
    id0: new FormControl(''),
    id1: new FormControl(''),
    id2: new FormControl(''),
    id3: new FormControl(''),
    id4: new FormControl('')
  }), new FormGroup({
    id0: new FormControl(''),
    id1: new FormControl(''),
    id2: new FormControl(''),
    id3: new FormControl(''),
    id4: new FormControl('')
  }), new FormGroup({
    id0: new FormControl(''),
    id1: new FormControl(''),
    id2: new FormControl(''),
    id3: new FormControl(''),
    id4: new FormControl('')
  }), new FormGroup({
    id0: new FormControl(''),
    id1: new FormControl(''),
    id2: new FormControl(''),
    id3: new FormControl(''),
    id4: new FormControl('')
  }), new FormGroup({
    id0: new FormControl(''),
    id1: new FormControl(''),
    id2: new FormControl(''),
    id3: new FormControl(''),
    id4: new FormControl('')
  })])
  myGroup = new FormGroup({
    forms: this.forms
  })
  constructor(private wordService: WordService, private statisticService: StatisticService, private userService: UserService){
    this.validWord = true
    this.id = 0
  }

  ngOnInit(): void {
    this.won = false
    this.lost = false
    this.newGame = true
  }

  ngAfterViewInit() {
    this.element = document.getElementById(String(0)) as HTMLElement
    this.element.focus()
  }

  public onLogoutUser(): void {
    localStorage.removeItem('currentUser');
  }

  public drawRandomWord(){
    this.lost = false
    this.newGame = true
    this.id = 0
    this.wordService.getRandomWord().subscribe(
      (word: Word) => {
        this.randomWord = word.wordName;
        this.wordId = word.wordId
        for (var i = 0; i < 6; i++) {
          this.resetForm(i)
        }
        for (var i = 0; i < 30; i++){
          const input = document.getElementById(String(i)) as HTMLElement
          input.style.backgroundColor = '#886f68'
          input.removeAttribute('readonly')
        }
        for (var it = 0; it < this.letters.length; it++) {
          const button = document.getElementById(this.letters[it].toUpperCase()) as HTMLElement
          button.style.backgroundColor = '#424c55'
        }
        this.won = false
        this.element = document.getElementById(String(0)) as HTMLElement
        this.element.focus()
      }
    )
  }

  public validWordCheck(wordInput: string): boolean{
    this.wordService.validWordCheck(wordInput).subscribe(
      () => {
        this.validWord = true;
      },
      (error: HttpErrorResponse) => {
        this.validWord = false;
      }
    )
    return this.validWord
  }

  public async wordCheck(j: number){
    await this.wordService.wordCheck(this.randomWord, this.wordName).then(value => {
      for (var it = 0; it < 5; it++) {
        const button = document.getElementById(this.wordName[it].toUpperCase()) as HTMLElement
        button.style.backgroundColor = value[it]
      }
      for (var i = j; i < j + 5; i++){
        const input = document.getElementById(String(i)) as HTMLElement
        input.style.backgroundColor = value[i % 5]
        input.setAttribute('readonly', String(true))
      }
      if (value.every((v: any) => v == value[0]) && value[0] == 'green'){
        this.won = true
        for (var i = j; i < 30; i++){
          const input = document.getElementById(String(i)) as HTMLElement
          input.setAttribute('readonly', String(true))
        }
        this.onAddNewStatistic()
      }
      if (this.id == 6 && this.won == false) {
        this.lost = true
        this.id = 7
        this.onAddNewStatistic()
      }
    })
  }


  onDigitInput(event: any){
    let element;
    if (event.code !== 'Backspace')
      element = event.srcElement.nextElementSibling;
    if (event.code === 'Backspace')
      element = event.srcElement.previousElementSibling;
    if(element == null)
      return;
    else
      element.focus();
    this.element = element
  }

  public onMakeWord(i: number){
    this.id += 1
    var wordName = ''
    wordName += this.forms.controls[i].controls.id0.value
    wordName += this.forms.controls[i].controls.id1.value
    wordName += this.forms.controls[i].controls.id2.value
    wordName += this.forms.controls[i].controls.id3.value
    wordName += this.forms.controls[i].controls.id4.value
    this.wordName = wordName
    this.wordService.validWordCheck(this.wordName).subscribe(
      () => {
        this.validWord = true;
        this.wordCheck(i * 5)
        if (5 * (i + 1) != 30) {
          const input = document.getElementById(String(5 * (i + 1))) as HTMLElement
          input.focus()
        }
        if (parseInt(this.inputId) % 5 == 4) {
          this.element = document.getElementById(String(parseInt(this.inputId) + 1)) as HTMLElement
          this.element.focus()
        }
        for (var it = 0; it < 5; it++) {
          this.letters.push(this.wordName.charAt(it).toUpperCase())
        }
      },
      (error: HttpErrorResponse) => {
        this.validWord = false;
        this.resetForm(i)
        const input = document.getElementById(String(5 * i)) as HTMLElement
        this.id -= 1
        this.element = input
        input.focus()
        setInterval(() => {this.resetValidation()}, 2000)
      }
    )
    }
  public resetValidation() {
    this.validWord = true
  }

  public resetForm(id: number){
    this.forms.controls[id].reset()
  }

  public insertValuesToForm(id: string, char: string) {
    const value = parseInt(id)
    if (value % 5 == 0) {
      this.forms.controls[this.id].controls.id0.setValue(char)
    }
    if (value % 5 == 1) {
      this.forms.controls[this.id].controls.id1.setValue(char)
    }
    if (value % 5 == 2) {
      this.forms.controls[this.id].controls.id2.setValue(char)
    }
    if (value % 5 == 3) {
      this.forms.controls[this.id].controls.id3.setValue(char)
    }
    if (value % 5 == 4) {
      this.forms.controls[this.id].controls.id4.setValue(char)
    }

  }

  public listenToVirtualKeyboard(id: string) {
    if (this.newGame == true) {
      this.element = document.getElementById('0') as HTMLElement
      this.newGame = false
    }
    const button = document.getElementById(id) as HTMLElement
    const input = this.element
    if (button.innerText == 'delete') {
      if (parseInt(input.id) % 5 == 0) {
        input.value = ''
        this.element.focus()
      }
      else {
        input.value = ''
        this.element = document.getElementById(String(parseInt(input.id) - 1)) as HTMLElement
        this.element.focus()
      }
    }
    else {
      if (parseInt(input.id) % 5 == 4) {
        this.inputId = input.id
        input.value = button.innerText.toLowerCase()
        this.insertValuesToForm(input.id, input.value)
      }
      else {
        input.value = button.innerText.toLowerCase()
        this.insertValuesToForm(input.id, input.value)
        this.element = document.getElementById(String(parseInt(input.id) + 1)) as HTMLElement
        this.element.focus()
      }
    }
  }

  public onAddNewStatistic() {
    this.userService.getUserByLogin(localStorage.getItem('currentUser') as string).subscribe(
      (response: User) => {
        this.statisticService.addNewStatistic({numberOfTries: this.id, user: response, correctWord: {wordId: this.wordId, wordName: this.randomWord}}).subscribe(
          (response: Statistic) => {

          }
        )
      }
    )
  }
}

