import { ChangeDetectionStrategy, Component, Inject, inject, OnInit } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import {
  MAT_DIALOG_DATA,
  MatDialogActions,
  MatDialogClose,
  MatDialogContent,
  MatDialogRef,
  MatDialogTitle,
} from '@angular/material/dialog';
import { InputComponent } from '../input/input.component';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ButtonComponent } from '../button/button.component';
import { MatIcon } from '@angular/material/icon';
import { User } from '../../types/User';
import { UserService } from '../../services/user.service';
import { NgIf } from '@angular/common';
import { AuthService } from '../../services/auth.service';
import { CPF_REGEX } from '../../utils/regexUtils';
import { InputErrorComponent } from '../input-error/input-error.component';

@Component({
  selector: 'app-user-details-modal',
  standalone: true,
  templateUrl: './user-details-modal.component.html',
  imports: [MatButtonModule, MatDialogActions, MatDialogClose, MatDialogTitle, MatDialogContent, InputComponent, ReactiveFormsModule, ButtonComponent, MatIcon, NgIf, InputErrorComponent],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class UserDetailsModalComponent implements OnInit {
  readonly dialogRef = inject(MatDialogRef<UserDetailsModalComponent>);
  form: FormGroup;
  submitted: boolean = false;

  constructor(private fb: FormBuilder, @Inject(MAT_DIALOG_DATA) public data: User, private userService: UserService, protected authService: AuthService) {
  }

  ngOnInit(): void {
    this.form = this.fb.group({
      name: [this.data.name, [Validators.required]],
      cpf: [this.data.cpf, [Validators.required, Validators.pattern(CPF_REGEX)]],
      email: [this.data.email, [Validators.required, Validators.email]],
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

  submitForm() {
    this.submitted = true;
    if (!this.form.valid) return;

    const { name, cpf, email } = this.form.value;

    this.userService.updateUser({ id: this.data.id, name, cpf, email })
      .subscribe(() => this.dialogRef.close());
  }

  deleteUser() {
    this.userService.deleteUser(this.data.id).subscribe(() => this.dialogRef.close());
  }
}
