import {Component} from '@angular/core';
import {TranslateService} from '@ngx-translate/core';
import {ICellRendererAngularComp} from 'ag-grid-angular';

@Component({
    selector: 'app-button-cell',
    template: `<button style="height: 20px" (click)="invokeParentMethod()" class="btn btn-primary btn-sm">{{ params.colDef.value.name }}</button>`,
    styles: [
        `.btn {
            line-height: 0.5;
            margin-left: 5px;
        }`
    ]
})

export class ButtonRendererComponent implements ICellRendererAngularComp {
    public params: any;

    agInit(params: any): void {
        this.params = params;
        // console.log(params);
    }

    public invokeParentMethod() {
        this.params.context.componentParent.buttonRendererCallback({
          source: this.params.colDef.value.source, data: this.params.data
        });
    }

    refresh(): boolean {
        return false;
    }
}
