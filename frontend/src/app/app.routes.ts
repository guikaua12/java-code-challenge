import { Routes } from '@angular/router';
import { HomeComponent } from './views/home/home.component';

export const routes: Routes = [
  {
    path: '',
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
      { path: 'login', loadComponent: () => import('./views/auth/login/login.component').then(c => c.LoginComponent) },
      {
        path: 'register',
        loadComponent: () => import('./views/auth/register/register.component').then(c => c.RegisterComponent),
      },
    ],
    // loadComponent: () => import('./views/auth/login/login.component').then(c => c.RegisterComponent),
  },
];
