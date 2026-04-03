package cl.td.otec_primavera.controlador;

import cl.td.otec_primavera.modelo.Curso;
import cl.td.otec_primavera.modelo.Estudiante;
import cl.td.otec_primavera.modelo.Evaluacion;
import cl.td.otec_primavera.repositorio.InstructorRepository;
import cl.td.otec_primavera.servicio.CursoService;
import cl.td.otec_primavera.servicio.EstudianteService;
import cl.td.otec_primavera.servicio.EvaluacionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/cursos")
public class CursoController {

    private final CursoService cursoService;
    private final InstructorRepository instructorRepository;
    private final EstudianteService estudianteService;
    private final EvaluacionService evaluacionService;

    public CursoController(CursoService cursoService, InstructorRepository instructorRepository,
            EstudianteService estudianteService, EvaluacionService evaluacionService) {
        this.cursoService = cursoService;
        this.instructorRepository = instructorRepository;
        this.estudianteService = estudianteService;
        this.evaluacionService = evaluacionService;
    }

    @GetMapping
    public String listarCursos(Model model) {
        List<Curso> cursos = cursoService.listarCursos();
        model.addAttribute("cursos", cursos);

        model.addAttribute("totalCursos", cursos.size());
        model.addAttribute("totalEstudiantes", estudianteService.listarEstudiantes().size());
        model.addAttribute("totalInstructores", instructorRepository.findAll().size());

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

    @GetMapping("/detalle/{id}")
    public String verDetalleCurso(@PathVariable("id") Integer id, Model model) {
        Curso curso = cursoService.obtenerCursoPorId(id);
        if (curso == null)
            return "redirect:/cursos";

        List<Estudiante> alumnos = estudianteService.obtenerEstudiantesPorCurso(id);
        List<Evaluacion> notas = evaluacionService.obtenerNotasPorCurso(id);

        int totalAlumnos = alumnos.size();
        int totalModulos = curso.getModulos().size();
        int totalEvaluacionesPosibles = totalAlumnos * totalModulos;

        long evaluacionesRealizadas = notas.stream().filter(n -> n.getNota() != null).count();
        double progreso = totalEvaluacionesPosibles == 0 ? 0
                : (double) evaluacionesRealizadas / totalEvaluacionesPosibles * 100;
        double promedio = notas.stream().filter(n -> n.getNota() != null).mapToDouble(Evaluacion::getNota).average()
                .orElse(0.0);

        model.addAttribute("curso", curso);
        model.addAttribute("totalAlumnos", totalAlumnos);
        model.addAttribute("progresoCurso", progreso);
        model.addAttribute("promedioCurso", promedio);

        return "curso-detalle";
    }

    @GetMapping("/libro-clases/{id}")
    public String verLibroClases(@PathVariable("id") Integer id, Model model) {
        Curso curso = cursoService.obtenerCursoPorId(id);
        if (curso == null)
            return "redirect:/cursos";

        List<Estudiante> alumnos = estudianteService.obtenerEstudiantesPorCurso(id);
        List<Evaluacion> notas = evaluacionService.obtenerNotasPorCurso(id);

        Map<String, String> notasTexto = new HashMap<>();
        Map<String, String> notasColor = new HashMap<>();

        if (notas != null) {
            for (Evaluacion n : notas) {
                if (n.getEstudiante() != null && n.getModulo() != null) {
                    String clave = n.getEstudiante().getIdEstudiante() + "_" + n.getModulo().getIdModulo();

                    if (n.getNota() != null) {
                        notasTexto.put(clave, String.format("%.1f", n.getNota()));
                        notasColor.put(clave, n.getNota() < 4.0 ? "text-danger" : "text-pastel-green");
                    }
                }
            }
        }

        model.addAttribute("curso", curso);
        model.addAttribute("alumnosMatriculados", alumnos);
        model.addAttribute("notasTexto", notasTexto);
        model.addAttribute("notasColor", notasColor);

        return "libro-clases";
    }
}