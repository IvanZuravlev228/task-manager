import {Component, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit{
  constructor(private http : HttpClient,
              private router: Router) {
  }

  ngOnInit(): void {
  }

  register() {
    this.router.navigate(['/users']);
  }

  login() {
    this.router.navigate(['/users/login']);
  }

  myProfile() {
    this.router.navigate(['/users/me']);
  }
}
