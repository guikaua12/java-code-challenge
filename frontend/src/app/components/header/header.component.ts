import { Component, DestroyRef, inject, OnInit } from '@angular/core';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { AuthService } from '../../services/auth.service';
import { GlobalVariablesService } from '../../services/global-variables.service';
import { User } from '../../types/User';
import { UserHeaderMenuComponent } from '../user-header-menu/user-header-menu.component';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [UserHeaderMenuComponent],
  templateUrl: './header.component.html',
})
export class HeaderComponent implements OnInit {
  private destroyRef = inject(DestroyRef);
  loggedUser: User | null = null;

  constructor(
    private authService: AuthService,
    private globalVariablesService: GlobalVariablesService,
  ) {
  }

  ngOnInit(): void {
    this.globalVariablesService.loggedUser$
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe(user => this.loggedUser = user);
  }


}
