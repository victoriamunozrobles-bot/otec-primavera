package cl.td.otec_primavera.servicio;

import cl.td.otec_primavera.modelo.Modulo;
import cl.td.otec_primavera.repositorio.ModuloRepository;
import org.springframework.stereotype.Service;

@Service
public class ModuloService {

    private final ModuloRepository moduloRepository;

    public ModuloService(ModuloRepository moduloRepository) {
        this.moduloRepository = moduloRepository;
    }

    public void guardarModulo(Modulo modulo) {
        moduloRepository.save(modulo);
    }

    public Modulo obtenerModuloPorId(Integer id) {
        return moduloRepository.findById(id).orElse(null);
    }

    public void eliminarModulo(Integer id) {
        moduloRepository.deleteById(id);
    }
}