package cl.td.otec_primavera.controlador;

import cl.td.otec_primavera.modelo.Estudiante;
import cl.td.otec_primavera.servicio.CursoService;
import cl.td.otec_primavera.servicio.EstudianteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/estudiantes")
public class EstudianteController {

    private final EstudianteService estudianteService;
    private final CursoService cursoService;

    public EstudianteController(EstudianteService estudianteService, CursoService cursoService) {
        this.estudianteService = estudianteService;
        this.cursoService = cursoService;
    }

    @GetMapping
    public String listarEstudiantes(Model model) {
        model.addAttribute("estudiantes", estudianteService.listarEstudiantes());
        return "estudiantes";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("estudiante", new Estudiante());
        model.addAttribute("listaCursos", cursoService.listarCursos());
        return "nuevo-estudiante";
    }

    @PostMapping
    public String guardarEstudiante(@ModelAttribute("estudiante") Estudiante estudiante) {
        estudianteService.guardarEstudiante(estudiante);
        return "redirect:/estudiantes";
    }
}
