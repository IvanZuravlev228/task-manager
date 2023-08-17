import {Component, Input} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {UserLogin} from "../../types/UserLogin";
import {CookieService} from "ngx-cookie-service";
import {UserService} from "../../services/user.service";
import {MessageService} from "../../services/message.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  userLogin : UserLogin = new UserLogin;

  constructor(private http : HttpClient,
              private userService: UserService,
              private cookie: CookieService,
              private messageService: MessageService,
              private router: Router) {
  }

  login() {
    this.userService.login(this.userLogin)
      .subscribe({
        next: (token) => {
          this.cookie.set("jwt-token", token.token);
          localStorage.setItem("email", this.userLogin.email);
          this.router.navigate(['/tasks']);
        },
        error: (error) => {
          if (error.status === 403) {
            this.messageService.showMessage("User with this email does not exist");
          } else {
            this.messageService.showMessage("Something went wrong");
          }
        }
      })
  }
}
