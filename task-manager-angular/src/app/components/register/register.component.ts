import { Component } from '@angular/core';
import {UserRegister} from "../../types/UserRegister";
import {HttpClient} from "@angular/common/http";
import {UserService} from "../../services/user.service";
import {MessageService} from "../../services/message.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  userRegister : UserRegister = new UserRegister();

  constructor(private http : HttpClient,
              private userService: UserService,
              private messageService: MessageService,
              private router: Router) {
  }

  register() {
    this.userService.register(this.userRegister)
      .subscribe({
        next: (newUser) => {
          this.cleanForm();
          this.router.navigate(['/users/login']);
        },
        error: (error) => {
          if (error.status == 403) {
            this.messageService.showMessage("User with this email already exist");
          } else {
            this.messageService.showMessage("Something went wrong");
          }
        }
      })
  }

  cleanForm() {
    this.userRegister = new UserRegister();
  }
}
