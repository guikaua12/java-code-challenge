import { Component, forwardRef, Input } from '@angular/core';
import {
  ControlContainer,
  ControlValueAccessor,
  FormGroupDirective,
  FormsModule,
  NG_VALUE_ACCESSOR,
  ReactiveFormsModule,
} from '@angular/forms';
import { noop } from 'rxjs';
import { twMerge } from 'tailwind-merge';
import { NgClass } from '@angular/common';

@Component({
  selector: 'app-input',
  templateUrl: './input.component.html',
  standalone: true,
  imports: [
    FormsModule,
    ReactiveFormsModule,
    NgClass,
  ],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      multi: true,
      useExisting: forwardRef(() => InputComponent),
    },
  ],
  viewProviders: [
    {
      provide: ControlContainer,
      useExisting: FormGroupDirective,
    },
  ],
})
export class InputComponent implements ControlValueAccessor {
  @Input() placeholder? = '';
  @Input() type? = 'text';
  @Input() label?: string = '';
  @Input() formControlName: string | number | null = '';
  @Input() ngClass = '';
  value?: string;
  isDisabled = false;


  protected onChange = noop;
  private onTouch = noop;

  writeValue(obj: any): void {
    if (obj === this.value)
      return;

    this.value = obj;
  }

  registerOnChange(fn: any): void {
    this.onChange = fn;
  }

  registerOnTouched(fn: any): void {
    this.onTouch = fn;
  }

  setDisabledState(isDisabled: boolean): void {
    this.isDisabled = isDisabled;
  }

  protected readonly twMerge = twMerge;
}
