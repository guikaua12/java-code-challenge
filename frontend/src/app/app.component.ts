import { Component, OnInit } from '@angular/core';
import { RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { AuthService } from './services/auth.service';
import { UserService } from './services/user.service';
import { GlobalVariablesService } from './services/global-variables.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  standalone: true,
  imports: [
    RouterOutlet,
    RouterLink,
    RouterLinkActive,
  ],
})
export class AppComponent implements OnInit {
  constructor(private authService: AuthService, private userService: UserService, private globalVariablesService: GlobalVariablesService) {
  }

  ngOnInit(): void {
    this.authService.initialUserFetch();
    this.userService.listUsers().subscribe(users => this.globalVariablesService.updateUsers(users));
  }

}
