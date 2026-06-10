import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Task } from '../model/task/task';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { environment } from 'src/environments/environment';



@Injectable({
  providedIn: 'root'
})
export class TaskService {

  private kanbanAppUrl = environment.kanbanAppUrl

  constructor(private http: HttpClient) { }

  updateTask(task: Task): Observable<Task> {
    let headers = new HttpHeaders({'Content-Type': 'application/json' });
    let options = { headers: headers };
    return this.http.put<Task>(
      this.kanbanAppUrl + '/tasks/' + task.id,
      task,
      options);
  }

  getTaskById(id: string): Observable<Task> {
    return this.http.get<Task>(this.kanbanAppUrl + '/tasks/' + id);
  }

  // НОВЫЙ МЕТОД: Отправить задачу во внешнюю систему (сделать системной)
  promoteToSystemTask(id: number): Observable<Task> {
    return this.http.post<Task>(
      this.kanbanAppUrl + '/tasks/' + id + '/promote-to-system',
      {}
    );
  }

  // ИСПРАВЛЕННЫЙ МЕТОД: Загрузить системные задачи из внешней системы для конкретной канбан-доски
  loadSystemTasks(kanbanId: number, limit: number = 3): Observable<string> {
    return this.http.get<string>(
      this.kanbanAppUrl + '/tasks/load-system-tasks/' + kanbanId + '?limit=' + limit,
      { responseType: 'text' as 'json' }
    );
  }
}
