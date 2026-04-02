package cl.td.otec_primavera.servicio;

import cl.td.otec_primavera.modelo.Curso;
import cl.td.otec_primavera.modelo.Estudiante;
import cl.td.otec_primavera.modelo.Evaluacion;
import cl.td.otec_primavera.repositorio.CursoRepository;
import cl.td.otec_primavera.repositorio.EstudianteRepository;
import cl.td.otec_primavera.repositorio.EvaluacionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CursoService {

    private final CursoRepository cursoRepository;
    private final EstudianteRepository estudianteRepository;
    private final EvaluacionRepository evaluacionRepository;

    public CursoService(CursoRepository cursoRepository,
            EstudianteRepository estudianteRepository,
            EvaluacionRepository evaluacionRepository) {
        this.cursoRepository = cursoRepository;
        this.estudianteRepository = estudianteRepository;
        this.evaluacionRepository = evaluacionRepository;
    }

    public List<Curso> listarCursos() {
        return cursoRepository.findAll();
    }

    public void guardarCurso(Curso curso) {
        cursoRepository.save(curso);
    }

    public Curso obtenerCursoPorId(Integer id) {
        Optional<Curso> curso = cursoRepository.findById(id);
        return curso.orElse(null);
    }

    @Transactional
    public void eliminarCurso(Integer idCurso) {

        List<Estudiante> estudiantes = estudianteRepository.findByCurso_IdCurso(idCurso);
        for (Estudiante e : estudiantes) {
            e.setCurso(null);
            estudianteRepository.save(e);
        }

        List<Evaluacion> evaluaciones = evaluacionRepository.findByModulo_Curso_IdCurso(idCurso);
        if (!evaluaciones.isEmpty()) {
            evaluacionRepository.deleteAll(evaluaciones);
        }

        cursoRepository.deleteById(idCurso);
    }
}