import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { User } from '../types/User';

@Injectable({ providedIn: 'root' })
export class GlobalVariablesService {
  private loggedUser = new BehaviorSubject<User | null>(null);
  public loggedUser$ = this.loggedUser.asObservable();

  updateUser(user: User | null): void {
    this.loggedUser.next(user);
  }

  getLoggedUser(): User | null {
    return this.loggedUser.value;
  }

  clearLoggedUser(): void {
    this.loggedUser.next(null);
  }
}
