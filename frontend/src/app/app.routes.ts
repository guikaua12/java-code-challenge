import { Routes } from '@angular/router';
import { HomeComponent } from './views/home/home.component';
import { LoginComponent } from './views/auth/login/login.component';
import { AuthComponent } from './views/auth/auth.component';
import { RegisterComponent } from './views/auth/register/register.component';

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
      { path: '', component: AuthComponent },
      { path: 'login', component: LoginComponent },
      { path: 'register', component: RegisterComponent },
    ],
    // loadComponent: () => import('./views/auth/login/login.component').then(c => c.RegisterComponent),
  },
];
