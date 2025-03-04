package com.webRutilandia.controladores;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.webRutilandia.dtos.UsuarioDto;
import com.webRutilandia.servicios.ApiServicio;
import com.webRutilandia.servicios.UsuarioServicio;
import com.webRutilandia.util.Utilidades;

import jakarta.servlet.http.HttpSession;

@Controller
public class UsuarioControlador {

    private final UsuarioServicio usuarioServicio;
    @Autowired
    private ApiServicio apiServicio;
    
   
    // Constructor para inyectar UsuarioServicio
    public UsuarioControlador(UsuarioServicio usuarioServicio) {
        this.usuarioServicio = usuarioServicio;
    }

   

    /**
     * Método para traer los parametros del formulario y enviar a la api los datos para que los guarde en base de datos
     * @param usuario
     * @param contrasenia
     * @param session
     * @return
     */
    @PostMapping("/registrar")
    public String registrarUsuario(@RequestParam("nombre") String nombre,
                                   @RequestParam("apellido1") String apellido1,
                                   @RequestParam("apellido2") String apellido2,
                                   @RequestParam("email") String email,
                                   @RequestParam("telefono") String telefono,
                                   @RequestParam("rol") String rol,
                                   @RequestParam("contrasenia") String contrasenia,
                                   HttpSession session) {
        // Crear el objeto DTO con los datos recibidos
        UsuarioDto usuarioNuevo = new UsuarioDto();
        usuarioNuevo.setNombre(nombre);
        usuarioNuevo.setApellidos(apellido1 + " " + apellido2); // Concatenación de apellidos
        usuarioNuevo.setEmail(email);
        usuarioNuevo.setTelefono(telefono);
        usuarioNuevo.setRol(rol);
        usuarioNuevo.setContrasenia(Utilidades.encriptarContrasenia(contrasenia)); // Encriptar contraseña

        try {
            // Llamar al servicio para registrar el usuario
            usuarioServicio.enviarRegistroUsuario(usuarioNuevo, session);
        } catch (Exception e) {
            e.printStackTrace();
            return "error"; // Si ocurre algún error, redirige a una página de error
        }

        // Redirigir a la página de éxito después de un registro exitoso
        return "redirect:/login.jsp"; // Nombre de la página de éxito (exito.jsp)
    }
    
    /**
     * Método para modificar un usuario
     * @param nombre
     * @param apellido1
     * @param apellido2
     * @param email
     * @param telefono
     * @param nuevaContrasenia
     * @param repNuevaContrasenia
     * @param contraseniaActual
     * @param session
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
    @PutMapping("/modificar")
    public String modificarUsuario(@RequestParam("nombre") String nombre,
                                   @RequestParam("apellido1") String apellido1,
                                   @RequestParam("apellido2") String apellido2,
                                   @RequestParam("email") String email,
                                   @RequestParam("telefono") String telefono,
                                   @RequestParam(value = "nuevaContrasenia", required = false) String nuevaContrasenia,
                                   @RequestParam(value = "repNuevaContrasenia", required = false) String repNuevaContrasenia,
                                   @RequestParam(value = "contraseniaActual", required = false) String contraseniaActual,
                                   HttpSession session) throws IOException, URISyntaxException {

        // Obtener el usuario desde la sesión para realizar la modificación
        UsuarioDto usuario = (UsuarioDto) session.getAttribute("usuario");

        System.out.println("Nombre: " + nombre);
        System.out.println("Apellido1: " + apellido1);
        System.out.println("Apellido2: " + apellido2);
        System.out.println("Email: " + email);
        System.out.println("Teléfono: " + telefono);
        
        // Si no hay usuario en sesión, redirigir a login
        if (usuario == null) {
            return "redirect:/login.jsp"; // Si el usuario no está autenticado, redirige al login
        }

        // Validar si el email está siendo usado por otro usuario
        if (!email.equals(usuario.getEmail()) && usuarioServicio.emailExiste(email)) {
            return "redirect:/errorEmailExistente.jsp"; // Redirige a una página de error si el email ya existe
        }
        
        // Si se quiere cambiar la contraseña
        if (nuevaContrasenia != null && !nuevaContrasenia.isEmpty()) {
            // Verificar que se haya proporcionado la contraseña actual
            if (contraseniaActual == null || contraseniaActual.isEmpty()) {
                return "redirect:/errorContraseniaIncorrecta.jsp";
            }
            // Verificar que la contraseña actual sea correcta
            if (!usuarioServicio.verificarContrasenia(usuario.getEmail(), contraseniaActual)) {
                return "redirect:/errorContraseniaIncorrecta.jsp";
            }
            // Verificar que la nueva contraseña coincida con su confirmación
            if (!nuevaContrasenia.equals(repNuevaContrasenia)) {
                return "redirect:/errorContraseniaNoCoincide.jsp";
            }
            // Encriptar la nueva contraseña y actualizar
            usuario.setContrasenia(Utilidades.encriptarContrasenia(nuevaContrasenia));
        }

        // Actualizar los demás campos del usuario
        usuario.setNombre(nombre);
        usuario.setApellidos(apellido1 + " " + apellido2);
        usuario.setEmail(email);
        usuario.setTelefono(telefono);

        // Enviar los datos a la API para actualizar el usuario
        try {
            String respuesta = usuarioServicio.actualizarUsuario(usuario);
            if ("success".equals(respuesta)) {
                return "redirect:/exito.jsp"; // Actualización exitosa
            } else {
                return "redirect:/errorActualizacion.jsp";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/error.jsp";
        }
    }
    
    
    /**
     * Método que pide los usuarios a la api para tenerlos en la parte controladora
     * @param modelo
     * @return una pagina  con una lista de los usuarios.
     */
    @GetMapping("/listar")
    public String listarUsuarios(Model modelo) {
        try {
            List<UsuarioDto> usuarios = usuarioServicio.obtenerUsuariosDesdeApi();
            System.out.println("🔍 Total usuarios obtenidos en el controlador: " + usuarios.size());
            for (UsuarioDto usuario : usuarios) {
                System.out.println("👤 " + usuario.getNombre() + " - " + usuario.getEmail());
            }
            modelo.addAttribute("usuarios", usuarios);
        } catch (Exception e) {
            modelo.addAttribute("mensaje", "Error al obtener usuarios: " + e.getMessage());
            System.out.println("❌ Error obteniendo usuarios: " + e.getMessage());
        }
        return "listarUsuarios.jsp"; // Asegúrate de que el JSP se llame listarUsuarios.jsp
    }


    /**
     * Método para eliminar un usuario.
     * @param id
     * @param redirectAttributes
     * @return devuelve a la pagina de la lista de usuarios
     */
    @GetMapping("/eliminar")
    public String eliminarUsuario(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        try {
            String resultado = usuarioServicio.eliminarUsuario(id);
            if ("success".equals(resultado)) {
                redirectAttributes.addFlashAttribute("mensaje", "Usuario eliminado correctamente.");
            } else {
                redirectAttributes.addFlashAttribute("mensaje", "Error al eliminar el usuario.");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Error al conectar con la API: " + e.getMessage());
        }

        return "redirect:/listar";
    }



}
