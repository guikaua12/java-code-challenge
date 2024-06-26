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

@Component({
  selector: 'app-input',
  templateUrl: './input.component.html',
  standalone: true,
  imports: [
    FormsModule,
    ReactiveFormsModule,
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
  value?: string;
  isDisabled = false;

  protected onChange = noop;
  private onTouch = noop;

  writeValue(obj: any): void {
    // If the new value is same as the current value we do nothing.
    // We don't wanna re-render the component.
    // It is just performance optimalisation
    if (obj === this.value)
      return;

    // store the new value in the class level variable
    this.value = obj;
    // notify the rendering engine the component change have to re-render
    // this._cdRef.markForCheck();
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

}
