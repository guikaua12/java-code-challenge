import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';
import { GlobalVariablesService } from '../services/global-variables.service';

export const homeGuard: CanActivateFn = (route, state) => {
  const router = inject(Router);
  const globalVariablesService = inject(GlobalVariablesService);

  if (!globalVariablesService.getLoggedUser()) {
    router.navigate(['auth/login']);
    return false;
  } else {
    return true;
  }
};
