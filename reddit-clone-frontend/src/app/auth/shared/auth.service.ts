import { HttpClient } from '@angular/common/http';
import { EventEmitter, Injectable, Output } from '@angular/core';
import { SignupRequestPayload } from '../signup/signup-request.payload';
import { Observable, map } from 'rxjs';
import { LoginRequestPayload } from '../login/login-request.payload';
import { LoginResponse } from '../login/login-response.payload';
import { LocalStorageService } from 'ngx-webstorage';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  // @Output() loggedIn: EventEmitter<boolean> = new EventEmitter();
  // @Output() username: EventEmitter<string> = new EventEmitter();

  // refreshTokenPayload = {
  //   refreshToken: this.getRefreshToken(),
  //   username: this.getUserName()
  // }

  constructor(private httpClient: HttpClient, private localStorage: LocalStorageService) {
  }

  signup(signupRequestPayload: SignupRequestPayload): Observable<any> {
    return this.httpClient.post('http://localhost:8080/api/auth/signup', signupRequestPayload, { responseType: 'text' });
  }

  login(loginRequestPayload: LoginRequestPayload): Observable<boolean> {
    return this.httpClient.post<LoginResponse>('http://localhost:8080/api/auth/login',
      loginRequestPayload).pipe(map(data => {
        this.localStorage.store('authenticationToken', data.authenticationToken);
        this.localStorage.store('username', data.username);
        this.localStorage.store('refreshToken', data.refreshToken);
        this.localStorage.store('expiresAt', data.expiresAt);
        // this.loggedIn.emit(true);
        // this.username.emit(data.username);
        return true;
      }));
  }
}
