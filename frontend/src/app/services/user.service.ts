import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../environment/environment';
import { Page } from '../types/Page';
import { User } from '../types/User';

const API_URL = environment.API;

@Injectable({
  providedIn: 'root',
})
export class UserService {
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
    }),
  };

  constructor(
    private http: HttpClient,
  ) {
  }

  listUsers() {
    return this.http.get<Page<User>>(API_URL + '/user/');
  }
}

