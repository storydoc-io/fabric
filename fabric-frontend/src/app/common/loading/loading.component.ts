import {Component, EventEmitter, OnInit, Output} from '@angular/core';

@Component({
    selector: 'app-loading',
    templateUrl: './loading.component.html',
    styleUrls: ['./loading.component.scss']
})
export class LoadingComponent implements OnInit {

    constructor() {
    }

    ngOnInit(): void {
    }

    @Output()
    onClick = new EventEmitter<void>()

    clicked() {
        this.onClick.emit()
    }

}
