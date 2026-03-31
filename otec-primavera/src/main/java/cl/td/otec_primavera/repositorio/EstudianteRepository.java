package cl.td.otec_primavera.repositorio;

import cl.td.otec_primavera.modelo.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Integer> {

    Optional<Estudiante> findByEmail(String email);

    List<Estudiante> findByCurso_IdCurso(Integer idCurso);
}