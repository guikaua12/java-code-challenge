import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink, RouterOutlet } from '@angular/router';
import { InputComponent } from '../../../components/input/input.component';
import { ButtonComponent } from '../../../components/button/button.component';
import { AuthService, RegisterRequest } from '../../../services/auth.service';
import { CommonModule } from '@angular/common';
import { InputErrorComponent } from '../../../components/input-error/input-error.component';
import { CPF_REGEX } from '../../../utils/regexUtils';

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
    CommonModule,
    InputErrorComponent,
  ],
  templateUrl: './register.component.html',
})
export class RegisterComponent implements OnInit {
  form!: FormGroup;
  submitted: boolean = false;

  constructor(private fb: FormBuilder, private authService: AuthService, private router: Router) {
  }

  ngOnInit(): void {
    this.form = this.fb.group({
      name: ['', [Validators.required]],
      cpf: ['', [Validators.required, Validators.pattern(CPF_REGEX)]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required],
    });
  }

  get name() {
    return this.form.get('name');
  }

  get cpf() {
    return this.form.get('cpf');
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

    const { name, cpf, email, password } = this.form.value as RegisterRequest;

    this.authService.register({ name, cpf, email, password })
      .subscribe(data => {
        this.router.navigate(['/']);
      });
  }
}
