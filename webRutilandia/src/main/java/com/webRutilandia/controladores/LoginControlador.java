package com.webRutilandia.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.webRutilandia.dtos.UsuarioDto;
import com.webRutilandia.servicios.LoginServicio;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginControlador {

	private final LoginServicio loginServicio;

    // Inyectar el servicio LoginServicio, no ApiServicio
    @Autowired
    public LoginControlador(LoginServicio loginServicio) {
        this.loginServicio = loginServicio;
    }

    @PostMapping("/iniciarSesion")
    public String iniciarSesion(@RequestParam("email") String email,
                                @RequestParam("contrasenia") String contrasenia,
                                HttpSession sesion) {
        try {
            // Intentar iniciar sesión
            String resultado = loginServicio.iniciarSesion(email, contrasenia, sesion);

            // Verificar si se obtuvo el usuario correctamente
            UsuarioDto usuario = (UsuarioDto) sesion.getAttribute("usuario");

            if (usuario != null) {
                // Redirigir según el rol
                if ("admin".equalsIgnoreCase(usuario.getRol())) {
                    return "redirect:/admin.jsp"; // Redirige a la página de administrador
                } else {
                    return "redirect:/usuario.jsp"; // Redirige a la página de usuario normal
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/login?error"; // En caso de fallo
    }
    
    @GetMapping("/cerrarSesion")
    public String cerrarSesion(HttpSession sesion) {
        // Invalida la sesión y elimina todos los atributos almacenados
        sesion.invalidate();

        System.out.println("Sesión cerrada correctamente.");

        // Redirigir al usuario a la página de login
        return "redirect:/login.jsp";
    }
}
