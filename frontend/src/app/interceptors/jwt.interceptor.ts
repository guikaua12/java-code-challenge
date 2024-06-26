import { inject } from '@angular/core';
import { HttpEvent, HttpHandlerFn, HttpInterceptorFn, HttpRequest } from '@angular/common/http';
import { AuthService } from '../services/auth.service';
import { Observable } from 'rxjs';

export const intercept: HttpInterceptorFn = (request: HttpRequest<any>, next: HttpHandlerFn): Observable<HttpEvent<any>> => {
  const authService = inject(AuthService);

  const authToken = authService.getToken();

  if (authToken) {
    const cloned = request.clone({
      setHeaders: { authorization: `Bearer ${authToken}` },
    });

    return next(cloned);
  }

  return next(request);
};
