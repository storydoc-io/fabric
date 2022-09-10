import {Injectable} from '@angular/core';
import {QueryControllerService} from "@fabric/services";
import {QueryResponseItemDto} from "@fabric/models";

@Injectable({
    providedIn: 'root'
})
export class ConsoleService {

    constructor(private queryControllerService: QueryControllerService) {
    }

    doQuery(environmentKey: string, systemComponentKey: string, attributes): Promise<QueryResponseItemDto> {
        return this.queryControllerService.doQueryUsingPost({
            body: {
                environmentKey,
                systemComponentKey,
                attributes
            }
        }).toPromise()
    }

}
