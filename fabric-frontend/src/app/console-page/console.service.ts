import {Injectable} from '@angular/core';
import {ConsoleControllerService} from "@fabric/services";
import {ConsoleDescriptorDto, ConsoleResponseItemDto, SnippetDto} from "@fabric/models";

@Injectable()
export class ConsoleService {

    constructor(private consoleControllerService: ConsoleControllerService) {
    }

    loadDescriptor(systemComponentKey: string) : Promise<ConsoleDescriptorDto>{
        return this.consoleControllerService.getDescriptorUsingGet({systemComponentKey})
            .toPromise()
    }

    runRequest(environmentKey: string, systemComponentKey: string, attributes): Promise<ConsoleResponseItemDto> {
        return this.consoleControllerService.runRequestUsingPost({
            body: {
                environmentKey,
                systemComponentKey,
                attributes
            }
        }).toPromise()
    }

    loadSnippets(systemComponentKey:string): Promise<SnippetDto[]> {
        return this.consoleControllerService.getSnippetsUsingGet({systemComponentKey}).toPromise()
    }

}
