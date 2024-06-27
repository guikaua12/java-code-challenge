import { ChangeDetectionStrategy, Component, inject, OnInit } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import {
  MatDialogActions,
  MatDialogClose,
  MatDialogContent,
  MatDialogRef,
  MatDialogTitle,
} from '@angular/material/dialog';
import { InputComponent } from '../input/input.component';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { ButtonComponent } from '../button/button.component';
import { MatIcon } from '@angular/material/icon';
import { CreateRequest, UserService } from '../../services/user.service';
import { NgIf } from '@angular/common';
import { AuthService } from '../../services/auth.service';
import { InputErrorComponent } from '../input-error/input-error.component';
import { CPF_REGEX } from '../../utils/regexUtils';
import { Role } from '../../types/Role';
import { MatFormField, MatLabel, MatOption, MatSelect } from '@angular/material/select';
import { MatInput } from '@angular/material/input';
import { Department } from '../../types/Department';
import { DepartmentService } from '../../services/department.service';

@Component({
  selector: 'app-create-user-modal',
  standalone: true,
  templateUrl: './create-user-modal.component.html',
  imports: [MatButtonModule, MatDialogActions, MatDialogClose, MatDialogTitle, MatDialogContent, InputComponent, ReactiveFormsModule, ButtonComponent, MatIcon, NgIf, InputErrorComponent, MatSelect, MatOption, FormsModule, MatLabel, MatFormField, MatInput],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class CreateUserModalComponent implements OnInit {
  readonly dialogRef = inject(MatDialogRef<CreateUserModalComponent>);
  form: FormGroup;
  submitted: boolean = false;
  roles: Role[] = ['MEMBER', 'ADMIN'];
  departments: Department[] = [];

  constructor(private fb: FormBuilder, private userService: UserService, protected authService: AuthService, private departmentService: DepartmentService) {
  }

  ngOnInit(): void {
    this.form = this.fb.group({
      name: ['', [Validators.required]],
      cpf: ['', [Validators.required, Validators.pattern(CPF_REGEX)]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required]],
      role: ['', [Validators.required]],
      departmentId: ['', [Validators.required]],
    });

    this.departmentService.listDepartments()
      .subscribe(departments => {
        this.departments = departments.content;
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

  get role() {
    return this.form.get('role');
  }

  get departmentId() {
    return this.form.get('departmentId');
  }

  submitForm() {
    if (!this.form.valid) return;

    const data = this.form.value as CreateRequest;

    this.userService.createUser(data)
      .subscribe(() => this.dialogRef.close());
  }
}
