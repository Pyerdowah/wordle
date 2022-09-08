import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Statistic } from './statistic';

@Injectable({providedIn: 'root'})
export class StatisticService {

  constructor(private http: HttpClient){}

  public getAllStatistic(): Observable<Statistic[]> {
    return this.http.get<Statistic[]>(`http://localhost:8080/getAllStatistics`);
  }

  public getStatistic(wordId: number): Observable<Array<number>> {
    return this.http.get<Array<number>>(`http://localhost:8080/wordStatistic/${wordId}`);
  }

  public getStatisticByLoginAndWord(wordId: bigint, userId: bigint): Observable<Statistic> {
    return this.http.get<Statistic>(`http://localhost:8080/getStatisticLoginAndWord/${wordId}/${userId}`);
  }

  public getUsersWords(userId: bigint): Observable<Array<number>> {
    return this.http.get<Array<number>>(`http://localhost:8080/wordStatisticUser/${userId}`);
  }

  public addNewStatistic(statistic: Statistic): Observable<Statistic> {
    return this.http.post<Statistic>(`http://localhost:8080/addNewStatistic`, statistic);
  }

  public updateStatistic(statisticId: bigint, statistic: Statistic): Observable<Statistic> {
    return this.http.put<Statistic>(`http://localhost:8080/updateUser/${statisticId}`, statistic);
  }

  public deleteStatistic(statisticId: bigint): Observable<void> {
    return this.http.delete<void>(`http://localhost:8080/deleteUser/${statisticId}`);
  }

}
