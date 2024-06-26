import { Component, DestroyRef, inject, OnInit } from '@angular/core';
import { MatIcon } from '@angular/material/icon';
import { GlobalVariablesService } from '../../services/global-variables.service';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { User } from '../../types/User';
import { NgIf, NgSwitch } from '@angular/common';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-user-header-menu',
  standalone: true,
  imports: [
    MatIcon,
    NgSwitch,
    NgIf,
    RouterLink,
  ],
  templateUrl: './user-header-menu.component.html',
})
export class UserHeaderMenuComponent implements OnInit {
  private destroyRef = inject(DestroyRef);
  protected loggedUser: User | null = null;

  constructor(
    private globalVariablesService: GlobalVariablesService,
  ) {
  }

  ngOnInit() {
    this.globalVariablesService.loggedUser$
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe(user => this.loggedUser = user);
  }

}
