import { LoginComponent } from './login/login.component';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http'
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})

export class AuthService {

  constructor(private http: HttpClient, private router: Router) { }

  getUSerDetails(username, password, callbackObj:LoginComponent) {
    return this.http.post('https://localhost:5001/api/users',
      {
        username,
        password
      }).subscribe(data => {
        
        console.log("authentication = ",data['authentication']);
        callbackObj.loginCheck(data, this.router);
        
      })
  }
}
