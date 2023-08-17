import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {User} from "../types/User";
import {environment} from "../../environment/environment";
import {UserRegister} from "../types/UserRegister";
import {UserLogin} from "../types/UserLogin";
import {Token} from "../types/Token";
import {CookieService} from "ngx-cookie-service";

@Injectable({
  providedIn: 'root'
})
export class UserService {
  constructor(private http: HttpClient,
              private cookie: CookieService) {
  }

  register(userRegister: UserRegister) {
    const body = JSON.stringify(userRegister);
    return this.http.post<User>(environment.backendURL + "/users/register", body, {
      headers: {
        "Content-Type": "application/json"
      }});
  }

  login(userLogin: UserLogin) {
    const body = JSON.stringify(userLogin);
    return this.http.post<Token>(environment.backendURL + "/users/login", body, {
      headers: {
        "Content-Type": "application/json"
      }});
  }

  getMe() {
    return this.http.get<User>(environment.backendURL + "/users/me", {
      headers: {
        "Authorization": "Bearer " + this.cookie.get("jwt-token")
      }});
  }
}
