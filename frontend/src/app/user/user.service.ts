import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from './user';

@Injectable({providedIn: 'root'})
export class UserService {

  constructor(private http: HttpClient){}

  public getAllUsers(): Observable<User[]> {
    return this.http.get<User[]>(`http://localhost:8080/getAllUsers`);
  }

  public getUserByLogin(login: string): Observable<User> {
    return this.http.get<User>(`http://localhost:8080/getUserByLogin/${login}`);
  }

  public registerNewUser(user: User): Observable<User> {
    return this.http.post<User>(`http://localhost:8080/registerNewUser`, user);
  }

  public updateUser(userId: bigint, user: User): Observable<User> {
    return this.http.put<User>(`http://localhost:8080/updateUser/${userId}`, user);
  }

  public deleteEmployee(userId: bigint): Observable<void> {
    return this.http.delete<void>(`http://localhost:8080/deleteUser/${userId}`);
  }

  public loginUser(user: User): Observable<User> {
    return this.http.post<User>(`http://localhost:8080/login`, user);
  }

  public logoutUser(user: User): Observable<User> {
    return this.http.post<User>(`http://localhost:8080/logout`, user);
  }
}
