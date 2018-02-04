import {Component} from '@angular/core';
import {ICellRendererAngularComp} from 'ag-grid-angular';

@Component({
    selector: 'app-checkbox-cell',
    template: `
    <label class="custom-control custom-checkbox">
        <input type="checkbox" class="custom-control-input" [checked]="params.value" (change)="onChange($event)">
        <span class="custom-control-indicator"></span>
        <span class="custom-control-description"></span>
    </label>`,
    styles: [`
    .custom-checkbox {
        margin-left: 5px;
    }
    .custom-control-indicator {
        top: 0.15rem;
    }`]
})

export class CheckBoxRendererComponent implements ICellRendererAngularComp {
    public params: any;

    constructor() { }

    agInit(params: any): void {
        this.params = params;
        // console.log(params);
    }

    public onChange(event: any) {
        this.params.data[this.params.colDef.field] = event.currentTarget.checked ? 1 : 0;
        this.params.context.componentParent.checkboxRendererCallback(this.params.data);
    }

    public invokeParentMethod() {

    }

    refresh(): boolean {
        return false;
    }
}
