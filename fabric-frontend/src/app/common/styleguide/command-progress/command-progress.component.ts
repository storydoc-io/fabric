import {Component, Input, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {ExecutionDto} from "@fabric/models";

interface Item {
    command: ExecutionDto
    depth: number
}

@Component({
    selector: 'app-command-progress',
    templateUrl: './command-progress.component.html',
    styleUrls: ['./command-progress.component.scss']
})
export class CommandProgressComponent implements OnInit, OnChanges {

    constructor() {
    }

    @Input()
    command: ExecutionDto

    items: Item[]

    ngOnChanges(changes: SimpleChanges): void {
        if (this.command) {
            let items: Item[] = []
            recursiveAddItems(this.command, items, 0)
            this.items = items
        }

        function recursiveAddItems(command: ExecutionDto, items: Item[], depth) {
            items.push({
                command,
                depth
            })
            command.children?.forEach(subCommand => recursiveAddItems(subCommand, items, depth + 1))
        }
    }

    ngOnInit(): void {
    }

    isComposite(item: Item): boolean | ExecutionDto[] {
        if (!(item.command.children && item.command.children.length > 0)) return false
        return item.command.children
    }

    itemWrapperStyle(item: Item) {
        let indent = item.depth * 50;
        return `margin-left: ${indent}px;`
    }

    itemChildren(item: Item): ExecutionDto[] {
        return item.command.children
    }


    itemProgressBarStyle(item: Item): string {
        return `width: ${item.command.percentDone}%;`
    }

    itemClasses(item: Item): string[] {
        return this.commandClasses(item.command)
    }

    commandClasses(command: ExecutionDto) {
        let classNames = ['progress-bar']
        switch (command.status) {
            case "RUNNING":
                classNames.push('bg-info', 'progress-bar-animated', 'progress-bar-striped')
                break
            case "PAUSED":
                classNames.push('bg-info')
                break
            case "UNRESPONSIVE":
                classNames.push('bg-warning', 'progress-bar-striped', 'progress-bar-animated')
                break
            case "DONE" :
                classNames.push('bg-success')
                break
            default:
                classNames.push('bg-danger')
        }
        return classNames
    }

    subCommandPercent(subCommand: ExecutionDto, subCommands: ExecutionDto[]) {
        return subCommand.percentDone
    }

    commandProgressBarStyle(command: ExecutionDto, subCommands: ExecutionDto[]) {
        return `width: ${command.percentDone/subCommands.length}%;`
    }

}
