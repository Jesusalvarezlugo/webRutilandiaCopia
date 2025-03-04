package com.webRutilandia.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


/**
 * Clase que se muestra al iniciar la aplicación
 */
@Controller
public class PrincipalControlador {

	@GetMapping("/")
    public String home(Model model) {
        // Definir la página de contenido dentro del layout
        model.addAttribute("pageTitle", "Inicio");
        model.addAttribute("pageContent", "indexContent.jsp");
        
        // Retorna la plantilla layout.jsp
        return "layout.jsp";
    }
}
