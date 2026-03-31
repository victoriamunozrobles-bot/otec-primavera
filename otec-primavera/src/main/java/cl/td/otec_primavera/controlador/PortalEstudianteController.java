package cl.td.otec_primavera.controlador;

import cl.td.otec_primavera.modelo.Estudiante;
import cl.td.otec_primavera.servicio.EstudianteService;
import cl.td.otec_primavera.servicio.EvaluacionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.security.Principal;

@Controller
public class PortalEstudianteController {

    private final EstudianteService estudianteService;
    private final EvaluacionService evaluacionService;

    public PortalEstudianteController(EstudianteService estudianteService, EvaluacionService evaluacionService) {
        this.estudianteService = estudianteService;
        this.evaluacionService = evaluacionService;
    }

    @GetMapping("/mi-portal")
    public String verMiPortal(Principal principal, Model model) {
        String emailUsuarioLogueado = principal.getName();
        Estudiante estudiante = estudianteService.obtenerEstudiantePorEmail(emailUsuarioLogueado);

        if (estudiante == null) {
            return "redirect:/login?error=sin_ficha";
        }

        model.addAttribute("estudiante", estudiante);

        if (estudiante.getCurso() != null) {
            model.addAttribute("notas", evaluacionService.obtenerNotasDeEstudiante(estudiante.getIdEstudiante()));
        }

        return "mi-portal";
    }
}