import { Component, inject, Input } from '@angular/core';
import { User } from '../../types/User';
import { RouterLink } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { UserDetailsModalComponent } from '../user-details-modal/user-details-modal.component';

@Component({
  selector: 'app-user',
  standalone: true,
  imports: [
    RouterLink,
  ],
  templateUrl: './user.component.html',
})
export class UserComponent {
  @Input() user: User;
  dialog = inject(MatDialog);

  openDialog(enterAnimationDuration: string, exitAnimationDuration: string): void {
    this.dialog.open(UserDetailsModalComponent, {
      width: '600px',
      enterAnimationDuration,
      exitAnimationDuration,
      data: this.user,
    });
  }
}
