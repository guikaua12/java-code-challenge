import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../environment/environment';
import { Page } from '../types/Page';
import { User } from '../types/User';
import { GlobalVariablesService } from './global-variables.service';
import { tap } from 'rxjs';

const API_URL = environment.API;

export type UpdateRequest = {
  id: number;
  name: string;
  cpf: string;
  email: string;
}

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
    private globalVariablesService: GlobalVariablesService,
  ) {
  }

  listUsers() {
    return this.http.get<Page<User>>(API_URL + '/user/');
  }

  updateUser({ id, name, cpf, email }: UpdateRequest) {
    return this.http.patch<User>(API_URL + '/user/', {
      id, name, cpf, email,
    }).pipe(tap(user => this.globalVariablesService.updateUserInList(user)));
  }

  deleteUser(id: number) {
    return this.http.delete<void>(`${API_URL}/user/${id}`).pipe(tap(() => this.globalVariablesService.deleteUserFromList(id)));
  }
}

