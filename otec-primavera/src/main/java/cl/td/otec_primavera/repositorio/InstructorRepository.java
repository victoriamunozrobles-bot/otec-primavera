package cl.td.otec_primavera.repositorio;

import cl.td.otec_primavera.modelo.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Integer> {
}