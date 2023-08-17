import {Component, OnInit} from '@angular/core';
import {User} from "../../types/User";
import {UserService} from "../../services/user.service";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit{
  user : User = new User();

  constructor(private userService: UserService) {
  }

  ngOnInit(): void {
    this.userService.getMe()
      .subscribe((currUser) => {
        this.user = currUser;
      })
  }
}
