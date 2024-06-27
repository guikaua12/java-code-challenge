import { Component, DestroyRef, inject, OnInit } from '@angular/core';
import { HeaderComponent } from '../../components/header/header.component';
import { NgForOf, NgIf, NgStyle } from '@angular/common';
import { UserService } from '../../services/user.service';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { User } from '../../types/User';
import { UserComponent } from '../../components/user/user.component';
import { ButtonComponent } from '../../components/button/button.component';
import { MatIcon } from '@angular/material/icon';
import { Page } from '../../types/Page';
import { GlobalVariablesService } from '../../services/global-variables.service';
import { AuthService } from '../../services/auth.service';
import { MatDialog } from '@angular/material/dialog';
import { CreateUserModalComponent } from '../../components/create-user-modal/create-user-modal.component';

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
    NgIf,
  ],
})
export class HomeComponent implements OnInit {
  private destroyRef = inject(DestroyRef);
  users: Page<User> | null;
  dialog = inject(MatDialog);

  constructor(private userService: UserService, protected authService: AuthService, private globalVariablesService: GlobalVariablesService) {
  }

  ngOnInit(): void {
    this.globalVariablesService.users$
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe(users => this.users = users);
  }

  openCreateUserDialog(enterAnimationDuration: string, exitAnimationDuration: string): void {
    this.dialog.open(CreateUserModalComponent, {
      width: '600px',
      enterAnimationDuration,
      exitAnimationDuration,
    });
  }

}
