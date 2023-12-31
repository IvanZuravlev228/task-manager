import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {AppComponent} from "./app.component";
import {LoginComponent} from "./components/login/login.component";
import {RegisterComponent} from "./components/register/register.component";
import {ProfileComponent} from "./components/profile/profile.component";
import {MainComponent} from "./components/main/main.component";
import {TasksComponent} from "./components/tasks/tasks.component";

const routes: Routes = [
  {path: '', component: AppComponent},
  {path: 'users/login', component: LoginComponent},
  {path: 'users', component: RegisterComponent},
  {path: 'users/me', component: ProfileComponent},
  {path: 'hello', component: MainComponent},
  {path: 'tasks', component: TasksComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
