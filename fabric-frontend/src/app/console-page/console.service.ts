import {Injectable} from '@angular/core';
import {ConsoleControllerService} from "@fabric/services";
import {ConsoleDescriptorDto, ConsoleResponseItemDto, NavItem, SnippetDto} from "@fabric/models";

@Injectable()
export class ConsoleService {

    constructor(private consoleControllerService: ConsoleControllerService) {
    }

    loadDescriptor(systemComponentKey: string) : Promise<ConsoleDescriptorDto>{
        return this.consoleControllerService.getDescriptorUsingGet({systemComponentKey})
            .toPromise()
    }

    runRequest(environmentKey: string, systemComponentKey: string, attributes, navItem: NavItem): Promise<ConsoleResponseItemDto> {
        return this.consoleControllerService.runRequestUsingPost({
            body: {
                environmentKey,
                systemComponentKey,
                attributes,
                navItem
            }
        }).toPromise()
    }

    loadSnippets(systemComponentKey:string): Promise<SnippetDto[]> {
        return this.consoleControllerService.getSnippetsUsingGet({systemComponentKey}).toPromise()
    }

    addSnippet(title: string, systemComponentKey: string, attributes: {}): Promise<SnippetDto[]> {
        return this.consoleControllerService.createSnippetUsingPost({
            systemComponentKey,
            body: {
                title,
                attributes
            }
        }).toPromise().then(()=> this.loadSnippets(systemComponentKey))

    }

    loadNavItems(systemComponentKey: string): Promise<NavItem[]>  {
        return this.consoleControllerService.getNavigationUsingPost({body: {
            systemComponentKey,
        }}).toPromise()
    }
}
