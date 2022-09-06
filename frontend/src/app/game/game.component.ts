import {Component, OnInit} from '@angular/core';
import {
  FormArray,
  FormBuilder,
  FormControl,
  FormGroup,
  NgForm,
  Validators
} from "@angular/forms";
import {HttpErrorResponse} from "@angular/common/http";
import {WordService} from "../word/word.service";
import {Word} from "../word/word";


@Component({
  selector: 'app-game',
  templateUrl: './game.component.html',
  styleUrls: ['./game.component.css'],
})
export class GameComponent implements OnInit {
  randomWord: any
  wordName: any
  validWord: any
  won: any
  letters = new FormGroup({
    id0: new FormControl(''),
    id1: new FormControl(''),
    id2: new FormControl(''),
    id3: new FormControl(''),
    id4: new FormControl('')
  });
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
  constructor(private wordService: WordService ){
    this.validWord = true
  }

  ngOnInit(): void {
    this.won = false
  }

  public onLogoutUser(): void {
    localStorage.removeItem('LOGGED');
  }

  public drawRandomWord(){
    var randomWord = ''
    this.wordService.getRandomWord().subscribe(
      (word: Word) => {
        this.randomWord = word.wordName;
      }
    )
    this.randomWord = randomWord
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
      for (var i = j; i < j + 5; i++){
        const input = document.getElementById(String(i)) as HTMLElement
        input.style.backgroundColor = value[i % 5]
        input.setAttribute('readonly', String(true))
      }
      if (value.every((v: any) => v == value[0]) && value[0] == 'green'){
        this.won = true
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
  }

  public onMakeWord(i: number){
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
      },
      (error: HttpErrorResponse) => {
        this.validWord = false;
        this.forms.controls[i].reset()
        setInterval(() => {this.resetValidation()}, 1000)
      }
    )
    }
  public resetValidation() {
    this.validWord = true
  }
}

