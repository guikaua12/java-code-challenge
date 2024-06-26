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

@Component({
  selector: 'app-user-details-modal',
  standalone: true,
  templateUrl: './user-details-modal.component.html',
  imports: [MatButtonModule, MatDialogActions, MatDialogClose, MatDialogTitle, MatDialogContent, InputComponent, ReactiveFormsModule, ButtonComponent, MatIcon],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class UserDetailsModalComponent implements OnInit {
  readonly dialogRef = inject(MatDialogRef<UserDetailsModalComponent>);
  form: FormGroup;

  constructor(private fb: FormBuilder, @Inject(MAT_DIALOG_DATA) public data: User, private userService: UserService) {
  }

  ngOnInit(): void {
    this.form = this.fb.group({
      name: [this.data.name, [Validators.required]],
      cpf: [this.data.cpf, [Validators.required]],
      email: [this.data.email, [Validators.required, Validators.email]],
    });
  }

  submitForm() {
    if (!this.form.valid) return;

    const { name, cpf, email } = this.form.value;

    this.userService.updateUser({ id: this.data.id, name, cpf, email })
      .subscribe(() => this.dialogRef.close());
  }

  deleteUser() {
    this.userService.deleteUser(this.data.id).subscribe(() => this.dialogRef.close());
  }
}
