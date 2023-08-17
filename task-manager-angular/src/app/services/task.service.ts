import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {CookieService} from "ngx-cookie-service";
import {environment} from "../../environment/environment";
import {TaskResponse} from "../types/TaskResponse";
import {TaskRequest} from "../types/TaskRequest";

@Injectable({
  providedIn: 'root'
})
export class TaskService {
  constructor(private http: HttpClient,
              private cookie: CookieService) { }

  addNewTask(taskForAdding : TaskRequest) {
    if (localStorage.getItem("email") !== null) {
      taskForAdding.ownerEmail = localStorage.getItem("email") as string;
    }
    const body = JSON.stringify(taskForAdding);
    return this.http.post<TaskResponse>(environment.backendURL + "/tasks", body, {
      headers: {
        "Authorization": "Bearer " + this.cookie.get("jwt-token"),
        "Content-Type": "application/json"
      }});
  }

  getAllTasks() {
    return this.http.get<TaskResponse[]>(environment.backendURL + "/tasks", {
      headers: {
        "Authorization": "Bearer " + this.cookie.get("jwt-token")
      }});
  }

  getMyTasks() {
    return this.http.get<TaskResponse[]>(environment.backendURL + "/tasks/me", {
      headers: {
        "Authorization": "Bearer " + this.cookie.get("jwt-token")
      }});
  }

  updateTask(taskId: number, newTask: TaskRequest) {
    const body = JSON.stringify(newTask);
    return this.http.put<TaskResponse>(environment.backendURL + "/tasks/" + taskId, body, {
      headers: {
        "Authorization": "Bearer " + this.cookie.get("jwt-token"),
        "Content-Type": "application/json"
      }});

  }

  getTaskById(id: number) {
    return this.http.get<TaskResponse>(environment.backendURL + "/tasks/" + id, {
      headers: {
        "Authorization": "Bearer " + this.cookie.get("jwt-token")
      }});
  }

  deleteTask(id: number) {
    return this.http.delete<void>(environment.backendURL + "/tasks/" + id, {
      headers: {
        "Authorization": "Bearer " + this.cookie.get("jwt-token")
      }});
  }
}
