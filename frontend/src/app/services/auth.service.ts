import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../environment/environment';
import { User } from '../types/User';
import { CookieService } from 'ngx-cookie-service';
import { tap } from 'rxjs';
import { GlobalVariablesService } from './global-variables.service';

const API_URL = environment.API;
const TOKEN = 'token';

export type LoginRequest = {
  email: string;
  password: string;
}

type RegisterRequest = {
  name: string,
  cpf: string,
  email: string,
  password: string,
  departmentId: number
}

type LoginResponse = {
  token: {
    id: number,
    email: string,
    token: string,
    expiresIn: number
  },
  user: User
}

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
    }),
  };

  constructor(
    private http: HttpClient,
    private cookieService: CookieService,
    private globalVariablesService: GlobalVariablesService,
  ) {
  }

  login({ email, password }: LoginRequest) {
    return this.http.post<LoginResponse>(API_URL + '/user/login', {
      email,
      password,
    }).pipe(
      tap((response: LoginResponse) => {
        this.cookieService.set(TOKEN, response.token.token, new Date(response.token.expiresIn));
        this.globalVariablesService.updateUser(response.user);
      }),
    );
  }

  register(data: RegisterRequest) {
    return this.http.post<LoginResponse>(API_URL + '/user/register', data)
      .pipe(
        tap((response: LoginResponse) => {
          this.cookieService.set(TOKEN, response.token.token, new Date(response.token.expiresIn));
          this.globalVariablesService.updateUser(response.user);
        }),
      );
  }

  logout(): void {
    this.cookieService.delete(TOKEN);
    this.globalVariablesService.updateUser(null);
  }

  getToken(): string {
    return this.cookieService.get(TOKEN);
  }
}

