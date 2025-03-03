package com.webRutilandia.servicios;

import java.io.IOException;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webRutilandia.dtos.UsuarioDto;

import jakarta.servlet.http.HttpSession;

@Service
public class LoginServicio {
    @Autowired
    private ApiServicio apiServicio;
    @Autowired 
    UsuarioServicio usuarioServicio;

    /**
     * Servicio para el logeo de sesion
     * @param email
     * @param contrasenia
     * @param sesion
     * @return
     * @throws URISyntaxException
     * @throws IOException
     */
    public String iniciarSesion(String email, String contrasenia, HttpSession sesion) throws URISyntaxException, IOException {
        UsuarioDto usuarioLogin = new UsuarioDto();
        usuarioLogin.setEmail(email);
        usuarioLogin.setContrasenia(contrasenia);
        
        // Enviar los datos a la API para autenticación y obtener el token
        String token = apiServicio.enviarLoginUsuario(usuarioLogin);

        if (token != null) {
            System.out.println("¡Inicio de sesión exitoso!");
            sesion.setAttribute("token", token);
            
            // Obtener los detalles del usuario, incluyendo su rol
            UsuarioDto usuarioCompleto = usuarioServicio.obtenerDetallesUsuario(email, token);
            
            if (usuarioCompleto != null) {
                System.out.println("Usuario autenticado: " + usuarioCompleto.getNombre() + " - Rol: " + usuarioCompleto.getRol());
                
                // Guardar los detalles en la sesión
                sesion.setAttribute("usuario", usuarioCompleto);
                
                return "success"; // Indica que el login fue correcto
            } else {
                System.out.println("[ERROR] No se pudieron obtener los detalles del usuario.");
                return "error";
            }
        } else {
            System.out.println("[ERROR] Credenciales incorrectas o fallo en la API.");
            return "error";
        }
    }
}
