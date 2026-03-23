package cl.td.otec_primavera.repositorio;

import cl.td.otec_primavera.modelo.Modulo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModuloRepository extends JpaRepository<Modulo, Integer> {
}