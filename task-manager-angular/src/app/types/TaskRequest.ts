export class TaskRequest {
  title: string = "";
  description: string = "";
  priority: string = "";
  status: string = "";
  start: Date = new Date();
  finish: Date = new Date();
  ownerEmail: string = "";
}
