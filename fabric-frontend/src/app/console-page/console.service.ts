import {Injectable} from '@angular/core';
import {ConsoleControllerService} from "@fabric/services";
import {ConsoleDescriptorDto, ConsoleResponseItemDto, NavItem, SnippetDto} from "@fabric/models";

@Injectable()
export class ConsoleService {

    constructor(private consoleControllerService: ConsoleControllerService) {
    }

    loadDescriptor(systemType: string) : Promise<ConsoleDescriptorDto>{
        return this.consoleControllerService.getDescriptorUsingGet({systemType})
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

    loadSnippets(systemType:string): Promise<SnippetDto[]> {
        return this.consoleControllerService.getSnippetsUsingGet({systemType}).toPromise()
    }

    addSnippet(title: string, systemType: string, attributes: {}): Promise<SnippetDto[]> {
        return this.consoleControllerService.createSnippetUsingPost({
            systemType,
            body: {
                title,
                attributes
            }
        }).toPromise().then(()=> this.loadSnippets(systemType))

    }

    loadNavItems(systemComponentKey: string): Promise<NavItem[]>  {
        return this.consoleControllerService.getNavigationUsingPost({body: {
            systemComponentKey,
        }}).toPromise()
    }
}
