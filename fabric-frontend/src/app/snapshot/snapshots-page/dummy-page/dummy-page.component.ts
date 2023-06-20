import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {ExecutionId} from "@fabric/models";
import {CommandControllerService} from "@fabric/services";

@Component({
    selector: 'app-dummy-page',
    templateUrl: './dummy-page.component.html',
    styleUrls: ['./dummy-page.component.scss']
})
export class DummyPageComponent implements OnInit {

    constructor(
        private route: ActivatedRoute,
        private commandControllerService: CommandControllerService
    ) {}

    executionId: ExecutionId

    command

    ngOnInit(): void {
        this.route.paramMap.subscribe((params) => {
            this.executionId = {id: params.get('executionId')}
            this.refresh()
        })
    }

    i = 0
    refresh() {
        this.commandControllerService.getExecutionInfoUsingGet({ id : this.executionId.id}).subscribe((dto) => {
            this.command = dto
            console.log('setting dto', dto)
            this.i++
            if (this.i < 200) {
                setTimeout(() => this.refresh(), 500)
            }
        })

    }

}
