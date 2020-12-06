import { LoginComponent } from './login/login.component';
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http'
import { Router } from '@angular/router';
import { RegisterComponent } from './register/register.component';

@Injectable({
  providedIn: 'root'
})

export class AuthService {

  constructor(private http: HttpClient, private router: Router) { }

  getUSerDetails(username, password, callbackObj: LoginComponent) {
    let headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Access-Control-Allow-Origin': '*'
    });
    var body = {
      "userName": username,
      "password": password
    }
    let options = {
      headers: headers,
    };
    return this.http.post('http://localhost:8080/api/userms/authenticateUser', body, options)
      .subscribe(data => {
        console.log("authentication = ", data);
        callbackObj.loginCheck(data, this.router);
      })
  }

  createUser(fName,
    lName,
    username,
    password,
    confirmPassword,
    callbackObj: RegisterComponent) {
    let headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Access-Control-Allow-Origin': '*'
    });

    var body = {
      "userName": username,
      "password": password,
      "confirmPassword": confirmPassword,
      "fName": fName,
      "lName": lName
    }

    let options = {
      headers: headers,
    };
    return this.http.post('http://localhost:8080/api/userms/createUser', body, options)
      .subscribe(data => {
        console.log("authentication = ", data);
        callbackObj.registerCheck(data, this.router);
      }
      )
  }

}
