package cl.td.otec_primavera.controlador;

import cl.td.otec_primavera.dto.UsuarioRegistroDTO;
import cl.td.otec_primavera.servicio.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistroController {

    private final UsuarioService usuarioService;

    public RegistroController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/registro")
    public String mostrarFormularioRegistro(Model model) {
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
    public String mostrarLogin() {
        return "login";
    }
}