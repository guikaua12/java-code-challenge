import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink, RouterOutlet } from '@angular/router';
import { InputComponent } from '../../../components/input/input.component';
import { ButtonComponent } from '../../../components/button/button.component';
import { AuthService, LoginRequest } from '../../../services/auth.service';
import { InputErrorComponent } from '../../../components/input-error/input-error.component';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    FormsModule,
    RouterOutlet,
    InputComponent,
    ReactiveFormsModule,
    ButtonComponent,
    RouterLink,
    InputErrorComponent,
    CommonModule,
  ],
  templateUrl: './login.component.html',
})
export class LoginComponent implements OnInit {
  form!: FormGroup;
  submitted: boolean = false;

  constructor(private fb: FormBuilder, private authService: AuthService, private router: Router) {
  }

  ngOnInit(): void {
    this.form = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required],
    });
  }

  get email() {
    return this.form.get('email');
  }

  get password() {
    return this.form.get('password');
  }

  submitForm() {
    this.submitted = true;
    if (!this.form.valid) return;

    const { email, password } = this.form.value as LoginRequest;

    this.authService.login({ email, password })
      .subscribe(data => {
        this.router.navigate(['/']);
      });
  }
}
