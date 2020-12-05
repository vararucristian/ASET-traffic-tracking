import { AuthService } from './../auth.service';
import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  incorrectData = false;
  constructor(private Auth: AuthService) { }

  ngOnInit(): void {
  }

  loginUser(event: Event , loginForm: NgForm){
    event.preventDefault();
    this.Auth.getUSerDetails(loginForm.value.inputUsername,loginForm.value.inputPassword, this);
  }

  loginCheck(data, router: Router){
    this.incorrectData=false;}  
  
}
