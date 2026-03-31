package cl.td.otec_primavera.servicio;

import cl.td.otec_primavera.modelo.Estudiante;
import cl.td.otec_primavera.repositorio.EstudianteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstudianteService {

    private final EstudianteRepository estudianteRepository;

    public EstudianteService(EstudianteRepository estudianteRepository) {
        this.estudianteRepository = estudianteRepository;
    }

    public List<Estudiante> listarEstudiantes() {
        return estudianteRepository.findAll();
    }

    public void guardarEstudiante(Estudiante estudiante) {
        if (estudiante.getEmail() != null) {
            estudiante.setEmail(estudiante.getEmail().toLowerCase());
        }
        estudianteRepository.save(estudiante);
    }

    public Estudiante obtenerEstudiantePorId(Integer id) {
        return estudianteRepository.findById(id).orElse(null);
    }

    public Estudiante obtenerEstudiantePorEmail(String email) {
        return estudianteRepository.findByEmail(email).orElse(null);
    }

    public List<Estudiante> obtenerEstudiantesPorCurso(Integer idCurso) {
        return estudianteRepository.findByCurso_IdCurso(idCurso);
    }
}