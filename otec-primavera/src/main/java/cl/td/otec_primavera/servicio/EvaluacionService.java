package cl.td.otec_primavera.servicio;

import cl.td.otec_primavera.modelo.Evaluacion;
import cl.td.otec_primavera.repositorio.EvaluacionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EvaluacionService {

    private final EvaluacionRepository evaluacionRepository;

    public EvaluacionService(EvaluacionRepository evaluacionRepository) {
        this.evaluacionRepository = evaluacionRepository;
    }

    public void guardarEvaluacion(Evaluacion evaluacion) {
        evaluacionRepository.save(evaluacion);
    }

    public List<Evaluacion> obtenerNotasDeEstudiante(Integer idEstudiante) {
        return evaluacionRepository.findByEstudiante_IdEstudiante(idEstudiante);
    }

    public Evaluacion obtenerNotaEspecifica(Integer idEstudiante, Integer idModulo) {
        return evaluacionRepository.findByEstudiante_IdEstudianteAndModulo_IdModulo(idEstudiante, idModulo)
                .orElse(null);
    }
}