export class TaskResponse {
  id: number = -1;
  title: string = "";
  description: string = "";
  priority: string = "";
  status: string = "";
  start: Date = new Date();
  finish: Date = new Date();
  ownerEmail: string = "";
}
