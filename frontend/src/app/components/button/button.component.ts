import { Component, Input } from '@angular/core';
import { twMerge } from 'tailwind-merge';
import { NgClass } from '@angular/common';
import { noop } from 'rxjs';

type ButtonVariant = 'primary' | 'secondary' | 'danger';

@Component({
  selector: 'app-button',
  standalone: true,
  imports: [
    NgClass,
  ],
  template: `
    <button [type]="type" [disabled]="disabled"
            [ngClass]="twMerge('w-full flex items-center outline-0 gap-2 rounded-md wood text-white px-4 py-2', buttonVariants[variant], ngClass)"
            (click)="onClick()">
      <ng-content></ng-content>
    </button>
  `,
})
export class ButtonComponent {
  @Input() type? = 'button';
  @Input() disabled? = false;
  @Input() ngClass = '';
  @Input() onClick: () => void = noop;
  @Input() variant: ButtonVariant = 'primary';

  protected readonly twMerge = twMerge;
  protected readonly buttonVariants: { [k in ButtonVariant]: string } = {
    primary: 'text-sm hover:bg-zinc-700 bg-neutral-900',
    secondary: 'bg-white border border-zinc-300 text-neutral-700 hover:bg-opacity-10',
    danger: 'bg-red-200 text-red-600 hover:bg-red-300',
  };
}
