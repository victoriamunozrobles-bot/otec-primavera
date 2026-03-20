package cl.td.otec_primavera.servicio;

import cl.td.otec_primavera.dto.UsuarioRegistroDTO;
import cl.td.otec_primavera.modelo.Estudiante;
import cl.td.otec_primavera.modelo.Usuario;
import cl.td.otec_primavera.repositorio.EstudianteRepository;
import cl.td.otec_primavera.repositorio.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final EstudianteRepository estudianteRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, EstudianteRepository estudianteRepository,
            PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.estudianteRepository = estudianteRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void registrarNuevoUsuario(UsuarioRegistroDTO dto) {

        Usuario usuario = new Usuario();
        usuario.setUsername(dto.getEmail());
        usuario.setPassword(passwordEncoder.encode(dto.getPassword()));
        usuario.setRol("ESTUDIANTE");
        usuarioRepository.save(usuario);

        Estudiante estudiante = new Estudiante();
        estudiante.setRut(dto.getRut());
        estudiante.setNombre(dto.getNombreCompleto());
        estudiante.setEmail(dto.getEmail());
        estudianteRepository.save(estudiante);
    }
}