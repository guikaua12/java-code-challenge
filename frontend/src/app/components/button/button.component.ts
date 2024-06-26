import { Component, Input } from '@angular/core';
import { twMerge } from 'tailwind-merge';
import { NgClass } from '@angular/common';
import { noop } from 'rxjs';

@Component({
  selector: 'app-button',
  standalone: true,
  imports: [
    NgClass,
  ],
  template: `
    <button [type]="type" [disabled]="disabled"
            [ngClass]="twMerge('w-full rounded-md wood bg-neutral-900 text-white px-2 py-1', ngClass)"
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
  protected readonly twMerge = twMerge;
}
