import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { User } from '../types/User';
import { Page } from '../types/Page';

@Injectable({ providedIn: 'root' })
export class GlobalVariablesService {
  private loggedUser = new BehaviorSubject<User | null>(null);
  public loggedUser$ = this.loggedUser.asObservable();
  private users = new BehaviorSubject<Page<User> | null>(null);
  public users$ = this.users.asObservable();

  updateUser(user: User | null): void {
    this.loggedUser.next(user);
  }

  getLoggedUser(): User | null {
    return this.loggedUser.value;
  }

  clearLoggedUser(): void {
    this.loggedUser.next(null);
  }

  getUsers(): Page<User> | null {
    return this.users.value;
  }

  updateUsers(users: Page<User> | null): void {
    this.users.next(users);
  }

  addUserToList(user: User): void {
    const users = this.getUsers();
    if (users) {
      users.content.push(user);
      this.updateUsers(users);
    }
  }

  updateUserInList(user: User): void {
    const users = this.getUsers();
    if (users) {
      const index = users.content.findIndex(u => u.id === user.id);
      if (index !== -1) {
        users.content[index] = user;
        this.updateUsers(users);
      }
    }
  }

  deleteUserFromList(id: number): void {
    const users = this.getUsers();
    if (users) {
      users.content = users.content.filter(u => u.id !== id);
      this.updateUsers(users);
    }
  }
}
