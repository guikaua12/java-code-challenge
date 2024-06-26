import { Component, Input } from '@angular/core';
import { User } from '../../types/User';
import { RouterLink } from '@angular/router';

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

  constructor() {
  }
}
