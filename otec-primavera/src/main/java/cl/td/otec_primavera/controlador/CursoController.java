package cl.td.otec_primavera.controlador;

import cl.td.otec_primavera.modelo.Curso;
import cl.td.otec_primavera.repositorio.InstructorRepository;
import cl.td.otec_primavera.servicio.CursoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cursos")
public class CursoController {

    private final CursoService cursoService;
    private final InstructorRepository instructorRepository;

    public CursoController(CursoService cursoService, InstructorRepository instructorRepository) {
        this.cursoService = cursoService;
        this.instructorRepository = instructorRepository;
    }

    @GetMapping
    public String listarCursos(Model model) {
        model.addAttribute("cursos", cursoService.listarCursos());
        return "cursos";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("curso", new Curso());
        model.addAttribute("listaInstructores", instructorRepository.findAll());
        return "nuevo-curso";
    }

    @PostMapping
    public String guardarCurso(@ModelAttribute("curso") Curso curso) {
        cursoService.guardarCurso(curso);
        return "redirect:/cursos";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable("id") Integer id, Model model) {
        Curso cursoExistente = cursoService.obtenerCursoPorId(id);

        model.addAttribute("curso", cursoExistente);

        model.addAttribute("listaInstructores", instructorRepository.findAll());

        return "nuevo-curso";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarCurso(@PathVariable("id") Integer id) {
        cursoService.eliminarCurso(id);

        return "redirect:/cursos";
    }
}