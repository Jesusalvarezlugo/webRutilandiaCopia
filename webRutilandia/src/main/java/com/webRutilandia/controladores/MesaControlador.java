package com.webRutilandia.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.webRutilandia.dtos.MesaDto;
import com.webRutilandia.servicios.MesaServicio;

import jakarta.servlet.http.HttpSession;

@Controller
public class MesaControlador {

	private final MesaServicio mesaServicio;

    @Autowired
    public MesaControlador(MesaServicio mesaServicio) {
        this.mesaServicio = mesaServicio;
    }

    /**
     * Maneja el envío del formulario para registrar una nueva mesa.
     */
    @PostMapping("/registrarMesa")
    public String registrarMesa(@RequestParam("nombreMesa") String nombreMesa,
                                @RequestParam("descripcionMesa") String descripcionMesa,
                                HttpSession session, RedirectAttributes redirectAttributes) {
        MesaDto nuevaMesa = new MesaDto();
        nuevaMesa.setNombreMesa(nombreMesa);
        nuevaMesa.setDescripcionMesa(descripcionMesa);

        try {
            String resultado = mesaServicio.enviarRegistroMesa(nuevaMesa, session);
            if ("success".equals(resultado)) {
                redirectAttributes.addFlashAttribute("mensaje", "Mesa registrada exitosamente.");
                return "redirect:/admin.jsp";
            } else {
                redirectAttributes.addFlashAttribute("error", "Hubo un problema al registrar la mesa.");
                return "redirect:/admin.jsp";
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error en el registro: " + e.getMessage());
            return "redirect:/admin.jsp";
        }
    }
}
