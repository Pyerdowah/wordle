import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Word } from './word';
import {WordGuessStatus} from "./wordGuessStatus";

@Injectable({providedIn: 'root'})
export class WordService {

  constructor(private http: HttpClient){}

  public getAllWords(): Observable<Word[]> {
    return this.http.get<Word[]>(`http://localhost:8080/getAllWords`);
  }

  public getRandomWord(): Observable<Word> {
    return this.http.get<Word>(`http://localhost:8080/getRandomWord`);
  }

  public validWordCheck(word: string): Observable<void> {
    return this.http.get<void>(`http://localhost:8080/validWord/${word}`);
  }

  public validCharCheck(letter: string): Observable<void> {
    return this.http.get<void>(`http://localhost:8080/validChar/${letter}`);
  }

  public wordCheck(correctWord: string, wordName: string): Promise<any> {
    return this.http.get<string[]>(`http://localhost:8080/wordCheck/${correctWord}/${wordName}`).toPromise();
  }

  public addNewWord(word: Word): Observable<Word> {
    return this.http.post<Word>(`http://localhost:8080/addNewWord`, word);
  }

  public updateWord(wordId: bigint, word: Word): Observable<Word> {
    return this.http.put<Word>(`http://localhost:8080/updateUser/${wordId}`, word);
  }

  public deleteWord(wordId: bigint): Observable<void> {
    return this.http.delete<void>(`http://localhost:8080/deleteWord/${wordId}`);
  }

}
