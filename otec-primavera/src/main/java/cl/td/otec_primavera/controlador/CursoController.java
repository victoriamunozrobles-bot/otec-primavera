package cl.td.otec_primavera.controlador;

import cl.td.otec_primavera.modelo.Curso;
import cl.td.otec_primavera.servicio.CursoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

// @Controller es la etiqueta que le dice a Spring: "Este es un garzón que sirve páginas web"
@Controller
// @RequestMapping("/cursos") significa que este garzón solo atiende las mesas
// (URLs) que empiecen con "/cursos"
@RequestMapping("/cursos")
public class CursoController {

    private final CursoService cursoService;

    public CursoController(CursoService cursoService) {
        this.cursoService = cursoService;
    }

    @GetMapping
    public String listarCursos(Model model) {
        model.addAttribute("cursos", cursoService.listarCursos());

        return "cursos";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("curso", new Curso());
        return "nuevo-curso";
    }

    @PostMapping
    public String guardarCurso(@ModelAttribute("curso") Curso curso) {
        cursoService.guardarCurso(curso);

        return "redirect:/cursos";
    }
}