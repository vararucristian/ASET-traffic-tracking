import { Component, OnInit } from '@angular/core';
import { AuthService } from './../auth.service';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['../login/login.component.css']
})
export class RegisterComponent implements OnInit {

  errorReason = "";
  incorrectData = false;
  constructor(private Auth: AuthService) { }

  ngOnInit(): void {
  }

  registerUser(event: Event , loginForm: NgForm){
    event.preventDefault();
    console.log("register user");
    this.Auth.createUser(loginForm.value.inputFirstName,
                        loginForm.value.inputLastName,
                        loginForm.value.inputUsername,
                        loginForm.value.inputPassword,
                        loginForm.value.inputConfirmPassword,
                        this                        
      );
    }
  registerCheck(data, router: Router){
    if(data['success'] == false)
    {
      console.log(data['reason']);
      this.errorReason = data['reason'];
      this.incorrectData = true;
    }
    else
    {
    this.incorrectData=false;
    router.navigate(['/login']);
    }    
  } 
}
