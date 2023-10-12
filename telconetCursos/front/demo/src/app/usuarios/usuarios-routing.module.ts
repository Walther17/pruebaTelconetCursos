import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ListaComponent } from './components/lista/lista.component';
import { CrearComponent } from './components/crear/crear.component';
import { EditarComponent } from './components/editar/editar.component';

const routes: Routes = [
  {
    path: '',
    children: [
      { path: 'lista-usuarios', component: ListaComponent},
      { path: 'crear-usuarios', component: CrearComponent},
      { path: 'editar-usuarios', component: EditarComponent}
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UsuariosRoutingModule { }
