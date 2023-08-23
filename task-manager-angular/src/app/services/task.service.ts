import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {CookieService} from "ngx-cookie-service";
import {environment} from "../../environment/environment";
import {TaskResponse} from "../types/TaskResponse";
import {TaskRequest} from "../types/TaskRequest";
import {SquadResponse} from "../types/SquadResponse";
import {SquadRequest} from "../types/SquadRequest";

@Injectable({
  providedIn: 'root'
})
export class TaskService {
  constructor(private http: HttpClient,
              private cookie: CookieService) { }

  updateTask(taskId: number, newTask: TaskRequest) {
    const body = JSON.stringify(newTask);
    return this.http.put<TaskResponse>(environment.backendURL + "/tasks/" + taskId, body, {
      headers: {
        "Authorization": "Bearer " + this.cookie.get("jwt-token"),
        "Content-Type": "application/json"
      }});
  }

  getAllSquadByUser() {
    return this.http.get<SquadResponse[]>(environment.backendURL + "/tasks/squads", {
      headers: {
        "Authorization": "Bearer " + this.cookie.get("jwt-token")
      }});
  }

  createNewSquad(squad: SquadRequest) {
    if (localStorage.getItem("email") !== null) {
      squad.ownerEmail = localStorage.getItem("email") as string;
    }
    const body = JSON.stringify(squad);
    return this.http.post<SquadResponse>(environment.backendURL + "/tasks/add/squad", body, {
      headers: {
        "Authorization": "Bearer " + this.cookie.get("jwt-token"),
        "Content-Type": "application/json"
      }});
  }

  deleteSquad(id: number) {
    return this.http.delete<void>(environment.backendURL + "/tasks/squads/" + id, {
      headers: {
        "Authorization": "Bearer " + this.cookie.get("jwt-token")
      }});
  }

  getAllTasksBySquad(squadId: number) {
    return this.http.get<TaskResponse[]>(environment.backendURL + "/tasks/squad/" + squadId, {
      headers: {
        "Authorization": "Bearer " + this.cookie.get("jwt-token"),
        "Content-Type": "application/json"
      }});
  }

  addTaskToSquad(task: TaskRequest, squadId: number) {
    if (localStorage.getItem("email") !== null) {
      task.ownerEmail = localStorage.getItem("email") as string;
    }
    const body = JSON.stringify(task);
    return this.http.post<TaskResponse>(environment.backendURL + "/tasks/add?squadId=" + squadId, body, {
      headers: {
        "Authorization": "Bearer " + this.cookie.get("jwt-token"),
        "Content-Type": "application/json"
      }})
  }

  deleteTaskFromSquad(squadId: number, taskId: number) {
    return this.http.delete<void>(environment.backendURL + "/tasks/squad/" + squadId + "/task/" + taskId, {
      headers: {
        "Authorization": "Bearer " + this.cookie.get("jwt-token"),
        "Content-Type": "application/json"
      }})
  }
}
