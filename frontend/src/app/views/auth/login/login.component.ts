import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { RouterOutlet } from '@angular/router';
import { InputComponent } from '../../../components/input/input.component';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    FormsModule,
    RouterOutlet,
    InputComponent,
    ReactiveFormsModule,
  ],
  templateUrl: './login.component.html',
})
export class LoginComponent implements OnInit {
  form!: FormGroup;

  constructor(private fb: FormBuilder) {
  }

  ngOnInit(): void {
    this.form = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required],
    });

    this.form.get('email')!.valueChanges.subscribe((value) => {
      console.log('email value changed:', value);
    });

    this.form.get('password')!.valueChanges.subscribe((value) => {
      console.log('password value changed:', value);
    });
  }

  submitForm() {
    const formData = this.form.value;
    console.log(formData);
  }
}
