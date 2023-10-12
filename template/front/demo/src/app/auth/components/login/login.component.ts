import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { LoginUsuario } from 'src/app/models/login-usuario';
import { AuthService } from 'src/app/services/auth/auth.service';
import { TokenService } from 'src/app/services/token/token.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  loginUsuario: LoginUsuario;
  nombreUsuario: string;
  password: string;
  isLogged = false;

  errMsj: string;

  constructor(
    private tokenService: TokenService,
    private authService: AuthService,
    private router: Router,
    private toastr: ToastrService
  ) { }

  ngOnInit() {
  this.isLogged = this.tokenService.isLogged();
  if (this.isLogged){
    this.router.navigate(["/"])
  }else{
    this.router.navigate(["/login"])
  }
  console.log(this.isLogged)
  
  }

  onLogin(): void {
    this.loginUsuario = new LoginUsuario(this.nombreUsuario, this.password);
    this.authService.login(this.loginUsuario).subscribe(
      data => {
        this.toastr.success('Redireccionando', 'Login Succesful', {
          timeOut: 200, positionClass: 'toast-top-center'
        });
        this.tokenService.setToken(data.token);
        this.router.navigate(['/listar-usuarios']);
      },
      err => {
        this.toastr.error('Username or password inválidas', 'Error', {
          timeOut: 2000, positionClass: 'toast-top-center'
        });
      }
    );
  
  }
  onLogOut(): void {
    this.tokenService.logOut();
  }

}