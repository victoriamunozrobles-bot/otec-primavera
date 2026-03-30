package cl.td.otec_primavera.controlador;

import cl.td.otec_primavera.modelo.Estudiante;
import cl.td.otec_primavera.servicio.EstudianteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.security.Principal;

@Controller
public class PortalEstudianteController {

    private final EstudianteService estudianteService;

    public PortalEstudianteController(EstudianteService estudianteService) {
        this.estudianteService = estudianteService;
    }

    @GetMapping("/mi-portal")
    public String verMiPortal(Principal principal, Model model) {
        String emailUsuarioLogueado = principal.getName();
        Estudiante estudiante = estudianteService.obtenerEstudiantePorEmail(emailUsuarioLogueado);

        if (estudiante == null) {
            return "redirect:/login?error=sin_ficha";
        }

        model.addAttribute("estudiante", estudiante);
        return "mi-portal";
    }
}