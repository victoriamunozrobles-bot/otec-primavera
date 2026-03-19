package cl.td.otec_primavera.servicio;

import cl.td.otec_primavera.modelo.Curso;
import cl.td.otec_primavera.repositorio.CursoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CursoService {

    private final CursoRepository cursoRepository;

    public CursoService(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
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

    public void eliminarCurso(Integer id) {
        cursoRepository.deleteById(id);
    }
}