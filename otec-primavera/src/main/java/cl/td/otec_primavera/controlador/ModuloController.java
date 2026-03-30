package cl.td.otec_primavera.controlador;

import cl.td.otec_primavera.modelo.Curso;
import cl.td.otec_primavera.modelo.Modulo;
import cl.td.otec_primavera.servicio.CursoService;
import cl.td.otec_primavera.servicio.ModuloService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/modulos")
public class ModuloController {

    private final ModuloService moduloService;
    private final CursoService cursoService;

    public ModuloController(ModuloService moduloService, CursoService cursoService) {
        this.moduloService = moduloService;
        this.cursoService = cursoService;
    }

    @GetMapping("/nuevo/{cursoId}")
    public String mostrarFormularioNuevo(@PathVariable("cursoId") Integer cursoId, Model model) {
        Curso curso = cursoService.obtenerCursoPorId(cursoId);
        Modulo modulo = new Modulo();
        modulo.setCurso(curso);

        model.addAttribute("modulo", modulo);
        return "formulario-modulo";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable("id") Integer id, Model model) {
        Modulo moduloExistente = moduloService.obtenerModuloPorId(id);
        model.addAttribute("modulo", moduloExistente);
        return "formulario-modulo";
    }

    @PostMapping("/guardar")
    public String guardarModulo(@ModelAttribute("modulo") Modulo modulo) {
        moduloService.guardarModulo(modulo);
        return "redirect:/cursos/detalle/" + modulo.getCurso().getIdCurso();
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarModulo(@PathVariable("id") Integer id) {
        Modulo modulo = moduloService.obtenerModuloPorId(id);
        Integer idCurso = modulo.getCurso().getIdCurso();

        moduloService.eliminarModulo(id);

        return "redirect:/cursos/detalle/" + idCurso;
    }
}