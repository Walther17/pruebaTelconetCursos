import { Component, ViewChild } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { NuevoUsuario } from 'src/app/models/nuevo-usuario';
import { Usuario } from 'src/app/models/usuario';
import { AuthService } from 'src/app/services/auth/auth.service';
import { TokenService } from 'src/app/services/token/token.service';
import { UsuariosService } from 'src/app/services/usuarios/usuario.service';

@Component({
  selector: 'app-lista-usuarios',
  templateUrl: './lista-usuarios.component.html',
  styleUrls: ['./lista-usuarios.component.scss']
})
export class ListaUsuariosComponent {

  @ViewChild('exampleModalNewUser') modalElement: any;
 

  usuarios: any[] = [];
  isAdmin = false;
  deleteUsuario: Usuario = {
    id: 0,
    nombre: '',
    apellido: '',
    nombreUsuario: '',
    estado: '',
    email: '',
    password: ''
  };

  // New User
  nuevoUsuario: NuevoUsuario;
  nombre: string;
  nombreUsuario: string;
  email: string;
  estado: string;
  apellido: string;
  password: string;
  errMsj: string;


  // Pagination
  pageSize = 5;
  currentPage = 1;

  constructor(
    private usuariosService: UsuariosService,
    private toastr: ToastrService,
    private tokenService: TokenService,
    private authService: AuthService,
  ) { }

  ngOnInit() {
    this.cargarUsuarios();
    this.isAdmin = this.tokenService.isAdmin();
  }

  cargarUsuarios(): void {
    this.usuariosService.lista().subscribe(
      data => {
        this.usuarios = data;
      },
      err => {
        console.log(err);
      }
    );
  }

  borrar(id: number) {
    this.usuariosService.deleteUsuario(id, this.deleteUsuario).subscribe(
      data => {
        this.toastr.success('Usuario Eliminado', 'OK', {
          timeOut: 2000,
          positionClass: 'toast-top-center'
        });
        this.cargarUsuarios();
      },
      err => {
        this.toastr.error(err.error.mensaje, 'Fail', {
          timeOut: 2000,
          positionClass: 'toast-top-center',
        });
      }
    );
  }

  goToPage(page: number): void {
    if (page >= 1 && page <= this.totalPages) {
      this.currentPage = page;
    }
  }

  get totalItems(): number {
    return this.usuarios.length;
  }

  get totalPages(): number {
    return Math.ceil(this.totalItems / this.pageSize);
  }

  get pages(): number[] {
    return Array.from({ length: this.totalPages }, (_, i) => i + 1);
  }

  onRegister(): void {
    this.nuevoUsuario = new NuevoUsuario(this.nombre,  this.apellido,  this.nombreUsuario, this.estado, this.email, this.password);
    this.authService.nuevo(this.nuevoUsuario).subscribe(
      data => {
        //alert(" User create!!!")
        this.toastr.success(data.mensaje, 'OK', {
          timeOut: 3000, positionClass: 'toast-top-center'
        });
        this.modalElement.nativeElement.modal('hide');


       },
      err => {
       //
        alert("Error, password or user inavlid")
        this.errMsj = err.error.mensaje;
        this.toastr.error(this.errMsj, 'Fail', {
          timeOut: 3000,  positionClass: 'toast-top-center',
        });
      }
    );
  }

}
