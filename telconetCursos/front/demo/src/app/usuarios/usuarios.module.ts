import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { UsuariosRoutingModule } from './usuarios-routing.module';
import { ListaComponent } from './components/lista/lista.component';
import { SharedModule } from '../shared/shared.module';
import { CrearComponent } from './components/crear/crear.component';
import { EditarComponent } from './components/editar/editar.component';
import { EliminarComponent } from './components/eliminar/eliminar.component';


@NgModule({
  declarations: [
    ListaComponent,
    CrearComponent,
    EditarComponent,
    EliminarComponent,
  ],
  imports: [
    CommonModule,
    UsuariosRoutingModule, 
    SharedModule,
  ],
})
export class UsuariosModule { }
