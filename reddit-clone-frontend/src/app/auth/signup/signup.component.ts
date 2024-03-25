import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { SignupRequestPayload } from './signup-request.payload';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../shared/auth.service';
import { LoginComponent } from '../login/login.component';
import { ToastrModule, ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-signup',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule, RouterModule, LoginComponent, ToastrModule],
  templateUrl: './signup.component.html',
  styleUrl: './signup.component.css'
})
export class SignupComponent {
  signupForm!: FormGroup;
  signupRequestPayload: SignupRequestPayload;

  constructor(private authService: AuthService, private router: Router, private toastr: ToastrService){
    this.signupRequestPayload = {
      username: '',
      password: '',
      email: ''
    }
  }
  ngOnInit(){
    this.signupForm = new FormGroup({
      username: new FormControl('', Validators.required),
      email: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', Validators.required)
    });
  }

  signup(){
    console.log("signup event fired");
    this.signupRequestPayload.email = this.signupForm.get('email')!.value;
    this.signupRequestPayload.username = this.signupForm.get('username')!.value;
    this.signupRequestPayload.password = this.signupForm.get('password')!.value;

    this.authService.signup(this.signupRequestPayload)
      .subscribe(data => {
        this.router.navigate(['/login'],
          { queryParams: { registered: 'true' } });
      }, error => {
        console.log(error);
        this.toastr.error('Registration Failed! Please try again');
      }
    );
  }
}
