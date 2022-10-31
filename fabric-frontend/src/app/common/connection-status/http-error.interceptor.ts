import {Injectable} from '@angular/core';
import {HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {catchError, tap} from "rxjs/operators";
import {ConnectionStatusService} from "./connection-status.service";

@Injectable()
export class HttpErrorInterceptor implements HttpInterceptor {

    constructor(private service: ConnectionStatusService) {
    }


    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        this.service.setStatus({ status: 'Calling', msg: null})
        return next.handle(request)
            .pipe(
                tap( () => this.service.setStatus({ status: 'OK', msg: null})),
                catchError((error: HttpErrorResponse) => {
                    let errorMsg = '';
                    if (error.error instanceof ErrorEvent) {
                        console.log('this is client side error');
                        errorMsg = `Error: ${error.error.message}`;
                        this.service.setStatus({ status:'Problem', msg: errorMsg})
                    } else {
                        console.log('this is server side error');
                        errorMsg = `Error Code: ${error.status},  Message: ${error.message}`;
                        this.service.setStatus({ status: 'Problem', msg: errorMsg})
                    }
                    console.log(errorMsg);
                    return throwError(errorMsg);
                })
            )
    }

}
