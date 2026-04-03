package cl.td.otec_primavera.controlador;

import cl.td.otec_primavera.modelo.Estudiante;
import cl.td.otec_primavera.modelo.Evaluacion;
import cl.td.otec_primavera.modelo.Modulo;
import cl.td.otec_primavera.servicio.EstudianteService;
import cl.td.otec_primavera.servicio.EvaluacionService;
import cl.td.otec_primavera.servicio.ModuloService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

import java.security.Principal;

@Controller
public class PortalEstudianteController {

    private final EstudianteService estudianteService;
    private final EvaluacionService evaluacionService;
    private final ModuloService moduloService;

    public PortalEstudianteController(EstudianteService estudianteService, EvaluacionService evaluacionService,
            ModuloService moduloService) {
        this.estudianteService = estudianteService;
        this.evaluacionService = evaluacionService;
        this.moduloService = moduloService;
    }

    @GetMapping("/mi-portal")
    public String verMiPortal(Principal principal, Model model) {
        String emailUsuarioLogueado = principal.getName();
        Estudiante estudiante = estudianteService.obtenerEstudiantePorEmail(emailUsuarioLogueado);

        if (estudiante == null) {
            return "redirect:/login?error=sin_ficha";
        }

        model.addAttribute("estudiante", estudiante);

        if (estudiante.getCurso() != null) {
            List<Evaluacion> notas = evaluacionService.obtenerNotasDeEstudiante(estudiante.getIdEstudiante());
            model.addAttribute("notas", notas);

            int totalModulos = estudiante.getCurso().getModulos().size();
            long notasIngresadas = notas.stream().filter(n -> n.getNota() != null).count();

            double progreso = totalModulos == 0 ? 0 : (double) notasIngresadas / totalModulos * 100;
            double promedio = notas.stream().filter(n -> n.getNota() != null).mapToDouble(Evaluacion::getNota).average()
                    .orElse(0.0);

            model.addAttribute("progresoEstudiante", progreso);
            model.addAttribute("promedioEstudiante", promedio);
        }

        return "mi-portal";
    }

    @GetMapping("/mi-portal/modulo/{idModulo}")
    public String verDetalleModulo(@PathVariable("idModulo") Integer idModulo, Principal principal, Model model) {
        String email = principal.getName();
        Estudiante estudiante = estudianteService.obtenerEstudiantePorEmail(email);

        if (estudiante == null || estudiante.getCurso() == null) {
            return "redirect:/mi-portal";
        }

        Modulo modulo = moduloService.obtenerModuloPorId(idModulo);

        if (!modulo.getCurso().getIdCurso().equals(estudiante.getCurso().getIdCurso())) {
            return "redirect:/mi-portal";
        }

        Evaluacion miNota = evaluacionService.obtenerNotaEspecifica(estudiante.getIdEstudiante(), idModulo);

        model.addAttribute("modulo", modulo);
        model.addAttribute("miNota", miNota);

        return "modulo-detalle";
    }
}