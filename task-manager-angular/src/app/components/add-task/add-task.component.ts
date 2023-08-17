import { Component } from '@angular/core';
import {TaskService} from "../../services/task.service";
import {TaskRequest} from "../../types/TaskRequest";
import {Router} from "@angular/router";
import {MessageService} from "../../services/message.service";

@Component({
  selector: 'app-add-task',
  templateUrl: './add-task.component.html',
  styleUrls: ['./add-task.component.css', '../main/main.component.css']
})
export class AddTaskComponent {
  task: TaskRequest = new TaskRequest();

  constructor(private taskService : TaskService,
              private router: Router,
              private messageService: MessageService) {
  }

  add() {
    this.taskService.addNewTask(this.task)
      .subscribe({
        next: () => {
          this.task = new TaskRequest();
        },
        error: () => {
          this.messageService.showMessage("Something went wrong 0(");
        }
      })
  }
}
