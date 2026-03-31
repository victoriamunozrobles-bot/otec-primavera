package cl.td.otec_primavera.repositorio;

import cl.td.otec_primavera.modelo.Evaluacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EvaluacionRepository extends JpaRepository<Evaluacion, Integer> {

    List<Evaluacion> findByEstudiante_IdEstudiante(Integer idEstudiante);

    Optional<Evaluacion> findByEstudiante_IdEstudianteAndModulo_IdModulo(Integer idEstudiante, Integer idModulo);

    List<Evaluacion> findByModulo_Curso_IdCurso(Integer idCurso);
}