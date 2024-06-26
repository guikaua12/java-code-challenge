import { Component, DestroyRef, inject, OnInit } from '@angular/core';
import { HeaderComponent } from '../../components/header/header.component';
import { NgForOf, NgStyle } from '@angular/common';
import { UserService } from '../../services/user.service';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { User } from '../../types/User';
import { UserComponent } from '../../components/user/user.component';
import { ButtonComponent } from '../../components/button/button.component';
import { MatIcon } from '@angular/material/icon';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  standalone: true,
  imports: [
    HeaderComponent,
    NgForOf,
    UserComponent,
    ButtonComponent,
    MatIcon,
    NgStyle,
  ],
})
export class HomeComponent implements OnInit {
  private destroyRef = inject(DestroyRef);

  usersList: User[] = [];

  constructor(private userService: UserService) {
  }

  ngOnInit(): void {
    this.userService.listUsers()
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe(users => this.usersList = users.content);
  }

}
