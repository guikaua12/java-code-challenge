import { Component, Input } from '@angular/core';
import { twMerge } from 'tailwind-merge';
import { NgClass } from '@angular/common';

@Component({
  selector: 'app-input-error',
  standalone: true,
  imports: [
    NgClass,
  ],
  template: `
    <div [ngClass]="twMerge('text-red-500 text-xs mt-1', class)">
      <ng-content></ng-content>
    </div>
  `,
})
export class InputErrorComponent {
  @Input() class: string;
  protected readonly twMerge = twMerge;
}
