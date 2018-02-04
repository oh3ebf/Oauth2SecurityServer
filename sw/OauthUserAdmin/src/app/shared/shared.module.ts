import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { LoaderComponent } from './loader/loader.component';
import { ButtonRendererComponent } from './button-renderer.component';
import { CheckBoxRendererComponent } from './checkbox-renderer.component';
import { DualListComponent } from './DualListBox/dual-list.component';
import { BreadcrumbComponent } from './breadcrumb/bread-crumb.component';


@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [
    LoaderComponent,
    ButtonRendererComponent,
    CheckBoxRendererComponent,
    DualListComponent,
    BreadcrumbComponent
  ],
  exports: [
    LoaderComponent,
    ButtonRendererComponent,
    CheckBoxRendererComponent,
    DualListComponent,
    BreadcrumbComponent
  ]
})
export class SharedModule { }
