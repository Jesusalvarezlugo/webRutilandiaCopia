package com.webRutilandia.controladores;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.webRutilandia.dtos.UsuarioDto;
import com.webRutilandia.servicios.LoginServicio;

import jakarta.servlet.http.HttpSession;

/**
 * Controlador que maneja el inicio y cierre de sesión.
 */
@Controller
public class LoginControlador {

    private static final Logger logger = LoggerFactory.getLogger(LoginControlador.class);
    private final LoginServicio loginServicio;

    @Autowired
    public LoginControlador(LoginServicio loginServicio) {
        this.loginServicio = loginServicio;
    }

    /**
     * Maneja el proceso de inicio de sesión.
     *
     * @param email      Correo del usuario
     * @param contrasenia Contraseña ingresada
     * @param sesion     Sesión actual
     * @return Redirección a la página según el rol del usuario o mensaje de error
     */
    @PostMapping("/iniciarSesion")
    public String iniciarSesion(@RequestParam("email") String email,
                                @RequestParam("contrasenia") String contrasenia,
                                HttpSession sesion) {
        logger.info("Intento de inicio de sesión para usuario: {}", email);
        
        try {
            String resultado = loginServicio.iniciarSesion(email, contrasenia, sesion);
            UsuarioDto usuario = (UsuarioDto) sesion.getAttribute("usuario");

            if (usuario != null) {
                logger.info("Inicio de sesión exitoso: Usuario '{}' con rol '{}'", usuario.getEmail(), usuario.getRol());

                if ("admin".equalsIgnoreCase(usuario.getRol())) {
                    return "redirect:/admin.jsp"; // Redirige a la página de administrador
                } else {
                    return "redirect:/usuario.jsp"; // Redirige a la página de usuario normal
                }
            } else {
                logger.warn("Inicio de sesión fallido: Credenciales incorrectas para usuario '{}'", email);
                return "redirect:/login.jsp?error=credencialesIncorrectas";
            }
        } catch (Exception e) {
            logger.error("Error en el inicio de sesión del usuario '{}': {}", email, e.getMessage(), e);
            return "redirect:/login.jsp?error=errorInterno";
        }
    }

    /**
     * Maneja el cierre de sesión del usuario.
     *
     * @param sesion Sesión del usuario
     * @return Redirección a la página de login
     */
    @GetMapping("/cerrarSesion")
    public String cerrarSesion(HttpSession sesion) {
        UsuarioDto usuario = (UsuarioDto) sesion.getAttribute("usuario");

        if (usuario != null) {
            logger.info("Cierre de sesión para usuario: {}", usuario.getEmail());
        } else {
            logger.info("Cierre de sesión sin usuario autenticado.");
        }

        sesion.invalidate();
        return "redirect:/login.jsp";
    }
}
