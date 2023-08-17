import {Component, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {TaskService} from "../../services/task.service";
import {TaskResponse} from "../../types/TaskResponse";
import {UserService} from "../../services/user.service";
import {MessageService} from "../../services/message.service";

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit{
  tasks: TaskResponse[] = [];

  constructor(private http : HttpClient,
              private router: Router,
              private taskService: TaskService,
              private userService: UserService,
              private messageService: MessageService) {
  }

  ngOnInit(): void {
    this.getAllTasks();
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

  getMyTasks() {
    this.taskService.getMyTasks()
      .subscribe((tasks) => {
        this.tasks = tasks
      })
  }

  getAllTasks() {
    this.taskService.getAllTasks()
      .subscribe({
        next: (tasks) => {
          this.tasks = tasks;
        },
        error: (error) => {
          this.messageService.showMessage("Something went wrong. Try to login or register");
        }
      })
  }

  updateTask(task: TaskResponse) {
    this.taskService.updateTask(task.id, task)
      .subscribe({
        next: (updatedTask) => {
        },
        error: (error) => {
          if (error.status === 403) {
            this.messageService.showMessage("This is not your task. You cannot update it");
          } else {
            this.messageService.showMessage("Something went wrong");
          }
        }
      })
  }

  deleteTask(task: TaskResponse) {
    if (task.ownerEmail === localStorage.getItem("email")) {
      this.taskService.deleteTask(task.id)
        .subscribe(() => {
          this.getMyTasks();
        })
    } else {
      this.messageService.showMessage("This is not your task. You cannot delete it");
    }
  }
}
