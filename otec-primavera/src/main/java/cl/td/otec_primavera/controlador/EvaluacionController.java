package cl.td.otec_primavera.controlador;

import cl.td.otec_primavera.modelo.Estudiante;
import cl.td.otec_primavera.modelo.Evaluacion;
import cl.td.otec_primavera.modelo.Modulo;
import cl.td.otec_primavera.servicio.EstudianteService;
import cl.td.otec_primavera.servicio.EvaluacionService;
import cl.td.otec_primavera.servicio.ModuloService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/evaluaciones")
public class EvaluacionController {

    private final EvaluacionService evaluacionService;
    private final EstudianteService estudianteService;
    private final ModuloService moduloService;

    public EvaluacionController(EvaluacionService evaluacionService, EstudianteService estudianteService,
            ModuloService moduloService) {
        this.evaluacionService = evaluacionService;
        this.estudianteService = estudianteService;
        this.moduloService = moduloService;
    }

    @GetMapping("/estudiante/{id}")
    public String verBoletin(@PathVariable("id") Integer idEstudiante, Model model) {
        Estudiante estudiante = estudianteService.obtenerEstudiantePorId(idEstudiante);

        if (estudiante.getCurso() == null) {
            return "redirect:/estudiantes";
        }

        List<Evaluacion> notas = evaluacionService.obtenerNotasDeEstudiante(idEstudiante);

        model.addAttribute("estudiante", estudiante);
        model.addAttribute("curso", estudiante.getCurso());
        model.addAttribute("modulos", estudiante.getCurso().getModulos());
        model.addAttribute("notas", notas);

        return "boletin";
    }

    @GetMapping("/calificar/{idEstudiante}/{idModulo}")
    public String mostrarFormularioCalificar(@PathVariable("idEstudiante") Integer idEstudiante,
            @PathVariable("idModulo") Integer idModulo, Model model) {

        Estudiante estudiante = estudianteService.obtenerEstudiantePorId(idEstudiante);
        Modulo modulo = moduloService.obtenerModuloPorId(idModulo);
        Evaluacion evaluacion = evaluacionService.obtenerNotaEspecifica(idEstudiante, idModulo);

        if (evaluacion == null) {
            evaluacion = new Evaluacion();
            evaluacion.setEstudiante(estudiante);
            evaluacion.setModulo(modulo);
        }

        model.addAttribute("evaluacion", evaluacion);
        return "formulario-evaluacion";
    }

    @PostMapping("/guardar")
    public String guardarEvaluacion(@ModelAttribute("evaluacion") Evaluacion evaluacion) {
        evaluacionService.guardarEvaluacion(evaluacion);

        Modulo moduloReal = moduloService.obtenerModuloPorId(evaluacion.getModulo().getIdModulo());
        Integer idCurso = moduloReal.getCurso().getIdCurso();

        return "redirect:/cursos/libro-clases/" + idCurso;
    }
}
