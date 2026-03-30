package cl.td.otec_primavera.controlador;

import cl.td.otec_primavera.dto.UsuarioRegistroDTO;
import cl.td.otec_primavera.servicio.UsuarioService;
import jakarta.servlet.http.HttpServletRequest; // <-- IMPORTANTE
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class RegistroController {

    private final UsuarioService usuarioService;

    public RegistroController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/")
    public String redireccionarSegunRol(HttpServletRequest request) {
        if (request.isUserInRole("ADMIN")) {
            return "redirect:/cursos";
        }
        return "redirect:/mi-portal";
    }

    @GetMapping("/registro")
    public String mostrarFormularioRegistro(Model model, Principal principal) {
        if (principal != null) {
            return "redirect:/";
        }
        model.addAttribute("registroDTO", new UsuarioRegistroDTO());
        return "registro";
    }

    @PostMapping("/registro")
    public String procesarRegistro(@ModelAttribute("registroDTO") UsuarioRegistroDTO dto) {
        if (!dto.getPassword().equals(dto.getRepetirPassword())) {
            return "redirect:/registro?error=passwords";
        }
        usuarioService.registrarNuevoUsuario(dto);
        return "redirect:/login?exito";
    }

    @GetMapping("/login")
    public String mostrarLogin(Principal principal) {
        if (principal != null) {
            return "redirect:/";
        }
        return "login";
    }
}