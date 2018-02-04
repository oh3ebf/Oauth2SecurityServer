import { Observable } from 'rxjs/Observable';

export interface ServiceActionInterfase {
    getAll(): Observable<any>;
    getOne(params: any): Observable<any>;
    add(params: any): Observable<any>;
    update(params: any): Observable<any>;
    delete(params: any): Observable<any>;
}
