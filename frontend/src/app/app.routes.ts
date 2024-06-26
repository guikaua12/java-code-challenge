import { Routes } from '@angular/router';
import { HomeComponent } from './views/home/home.component';
import { homeGuard } from './guards/home.guard';
import { loginGuard } from './guards/login.guard';

export const routes: Routes = [
  {
    path: '',
    canActivate: [homeGuard],
    component: HomeComponent,
    children: [
      {
        path: '',
        redirectTo: '/home',
        pathMatch: 'full',
      },
      { path: 'home', component: HomeComponent },
    ],
  },

  {
    path: 'auth',
    children: [
      { path: '', loadComponent: () => import('./views/auth/auth.component').then(c => c.AuthComponent) },
      {
        path: 'login',
        canActivate: [loginGuard],
        loadComponent: () => import('./views/auth/login/login.component').then(c => c.LoginComponent),
      },
      {
        path: 'register',
        canActivate: [loginGuard],
        loadComponent: () => import('./views/auth/register/register.component').then(c => c.RegisterComponent),
      },
    ],
  },
];
