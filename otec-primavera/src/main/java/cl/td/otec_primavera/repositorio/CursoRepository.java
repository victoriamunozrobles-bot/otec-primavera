package cl.td.otec_primavera.repositorio;

import cl.td.otec_primavera.modelo.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Integer> {
    List<Curso> findByNombreContainingIgnoreCase(String nombre);
}
