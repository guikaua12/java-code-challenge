import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../environment/environment';
import { Page } from '../types/Page';
import { Department } from '../types/Department';

const API_URL = environment.API;

@Injectable({
  providedIn: 'root',
})
export class DepartmentService {
  constructor(
    private http: HttpClient,
  ) {
  }

  listDepartments() {
    return this.http.get<Page<Department>>(API_URL + '/department/');
  }

}

