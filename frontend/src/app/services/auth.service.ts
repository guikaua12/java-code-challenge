import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../environment/environment';
import { User } from '../types/User';
import { tap } from 'rxjs';
import { GlobalVariablesService } from './global-variables.service';
import { Router } from '@angular/router';

const API_URL = environment.API;
const TOKEN = 'token';

export type LoginRequest = {
  email: string;
  password: string;
}

export type RegisterRequest = {
  name: string,
  cpf: string,
  email: string,
  password: string,
  departmentId?: number
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
    private globalVariablesService: GlobalVariablesService,
    private router: Router,
  ) {
  }

  login({ email, password }: LoginRequest) {
    return this.http.post<LoginResponse>(API_URL + '/user/login', {
      email,
      password,
    }).pipe(
      tap((response: LoginResponse) => {
        localStorage.setItem(TOKEN, response.token.token);
        this.globalVariablesService.updateUser(response.user);
      }),
    );
  }

  register(data: RegisterRequest) {
    return this.http.post<LoginResponse>(API_URL + '/user/register', data)
      .pipe(
        tap((response: LoginResponse) => {
          localStorage.setItem(TOKEN, response.token.token);
          this.globalVariablesService.updateUser(response.user);
        }),
      );
  }

  logout(): void {
    localStorage.removeItem(TOKEN);
    this.globalVariablesService.updateUser(null);
    this.router.navigate(['auth/login']);
  }

  getToken(): string | null {
    return localStorage.getItem(TOKEN);
  }

  initialUserFetch() {
    return this.http.get<User>(API_URL + '/user/token')
      .subscribe({
        next: user => {
          console.log(user);
          this.router.navigate(['/']);
          this.globalVariablesService.updateUser(user);
        },
        error: () => {
          this.globalVariablesService.clearLoggedUser();
          localStorage.removeItem(TOKEN);
        },
      });
  }

  isAdmin(): boolean {
    return this.globalVariablesService.getLoggedUser()?.role === 'ADMIN';
  }
}

