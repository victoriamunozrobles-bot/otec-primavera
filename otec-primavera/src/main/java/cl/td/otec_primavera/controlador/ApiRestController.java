package cl.td.otec_primavera.controlador;

import cl.td.otec_primavera.modelo.Curso;
import cl.td.otec_primavera.modelo.Estudiante;
import cl.td.otec_primavera.servicio.CursoService;
import cl.td.otec_primavera.servicio.EstudianteService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class ApiRestController {

    private final CursoService cursoService;
    private final EstudianteService estudianteService;

    public ApiRestController(CursoService cursoService, EstudianteService estudianteService) {
        this.cursoService = cursoService;
        this.estudianteService = estudianteService;
    }

    @GetMapping("/cursos")
    public List<Map<String, Object>> obtenerCursosJson() {
        List<Curso> cursos = cursoService.listarCursos();

        return cursos.stream().map(curso -> Map.<String, Object>of(
                "codigo", curso.getCodigo(),
                "nombre", curso.getNombre(),
                "instructor", curso.getInstructor(),
                "duracion_horas", curso.getDuracionHoras(),
                "categoria", curso.getCategoria(),
                "estado", curso.getActivo() ? "Activo" : "Inactivo")).collect(Collectors.toList());
    }

    @GetMapping("/estudiantes")
    public List<Map<String, Object>> obtenerEstudiantesJson() {
        List<Estudiante> estudiantes = estudianteService.listarEstudiantes();

        return estudiantes.stream().map(est -> Map.<String, Object>of(
                "rut", est.getRut(),
                "nombre", est.getNombre(),
                "email", est.getEmail(),
                "curso_actual", est.getCurso() != null ? est.getCurso().getNombre() : "Sin asignar"))
                .collect(Collectors.toList());
    }
}