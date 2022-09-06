import {Component, OnInit} from '@angular/core';
import {
  FormArray,
  FormControl,
  FormGroup,
} from "@angular/forms";
import {HttpErrorResponse} from "@angular/common/http";
import {WordService} from "../word/word.service";
import {Word} from "../word/word";
import Keyboard from 'simple-keyboard'

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
  id: any
  letters:string[] = []
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
    this.id = 0
  }

  ngOnInit(): void {
    this.won = false
  }

  public onLogoutUser(): void {
    localStorage.removeItem('LOGGED');
  }

  public drawRandomWord(){
    this.wordService.getRandomWord().subscribe(
      (word: Word) => {
        this.randomWord = word.wordName;
        for (var i = 0; i < this.randomWord.length; i++) {
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
        const input = document.getElementById(String(5 * (i + 1))) as HTMLElement
        input.focus()
        for (var it = 0; it < 5; it++) {
          this.letters.push(this.wordName.charAt(it).toUpperCase())
        }
      },
      (error: HttpErrorResponse) => {
        this.validWord = false;
        this.resetForm(i)
        const input = document.getElementById(String(5 * i)) as HTMLElement
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

  public listenToVirtualKeyboard(id: string){
    const button = document.getElementById(id) as HTMLElement
    console.log(button.innerText.toLowerCase())
  }
}

