import { Routes } from '@angular/router';
import { SignupComponent } from './auth/signup/signup.component';
import { LoginComponent } from './auth/login/login.component';

export const routes: Routes = [
    {path: 'sign-up', component: SignupComponent},
    {path: 'login', component: LoginComponent}
];
