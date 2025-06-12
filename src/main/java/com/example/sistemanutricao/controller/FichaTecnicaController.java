package com.example.sistemanutricao.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import com.example.sistemanutricao.model.Status;
import com.example.sistemanutricao.model.StatusCriacao;
import com.example.sistemanutricao.record.FichaTecnicaDTO.FichaTecnicaUpdateDTO;
import com.example.sistemanutricao.record.PerfilNutricionalDTO.PerfilNutricionalGetDTO;
import com.example.sistemanutricao.service.PerfilNutricionalService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.sistemanutricao.model.Categoria;
import com.example.sistemanutricao.record.FichaTecnicaDTO.FichaTecnicaCreateDTO;
import com.example.sistemanutricao.record.FichaTecnicaDTO.FichaTecnicaGetDTO;
import com.example.sistemanutricao.record.IngredienteDTO.IngredienteGetDTO;
import com.example.sistemanutricao.record.PerfilNutricionalDTO.PerfilNutricionalCreateDTO;
import com.example.sistemanutricao.record.PreparacaoDTO.PreparacaoCreateDTO;
import com.example.sistemanutricao.service.FichaTecnicaService;
import com.example.sistemanutricao.service.IngredienteService;

import static com.example.sistemanutricao.model.Status.ATIVA;

@Controller
@RequestMapping("/fichas")
public class FichaTecnicaController {
        private final FichaTecnicaService fichaTecnicaService;
        private final IngredienteService ingredienteService;
        private final PerfilNutricionalService perfilService;

        public FichaTecnicaController(FichaTecnicaService fichaTecnicaService,
                                      IngredienteService ingredienteService,
                                      PerfilNutricionalService perfilService) {
            this.fichaTecnicaService = fichaTecnicaService;
            this.ingredienteService = ingredienteService;
            this.perfilService = perfilService;
        }

        @GetMapping
        public String listarTodasFichas(Model model) {
            List<FichaTecnicaGetDTO> fichas = fichaTecnicaService.buscarPorStatus(ATIVA);
            model.addAttribute("fichas", fichas);
            model.addAttribute("statusAtual", ATIVA);
            return "Ficha/List";
        }

        @GetMapping("/custoPerCapita")
        public String buscarPorCustoPerCapita(
                @RequestParam BigDecimal custoPerCapita,
                Model model) {
            List<FichaTecnicaGetDTO> fichas = fichaTecnicaService.buscarPorCustoPerCapita(custoPerCapita);
            model.addAttribute("fichas", fichas);
            return "Ficha/List";
        }

        @GetMapping("/por-status")
        public String buscarPorStatus(
                @RequestParam Status status,
                Model model) {
            List<FichaTecnicaGetDTO> fichas = fichaTecnicaService.buscarPorStatus(status);
            model.addAttribute("fichas", fichas);
            model.addAttribute("statusAtual", status);
            return "Ficha/List";
        }

        @GetMapping("/por-statusCriacao")
        public String buscarPorStatus(
                @RequestParam StatusCriacao statusCriacao,
                Model model) {
            List<FichaTecnicaGetDTO> fichas = fichaTecnicaService.buscarPorStatusCriacao(statusCriacao);
            model.addAttribute("fichas", fichas);
            model.addAttribute("statusCriacaoAtual", statusCriacao);
            return "Ficha/List";
        }

        @GetMapping("/custoTotal")
        public String buscarPorCustoTotal(
                @RequestParam BigDecimal custoTotal,
                Model model) {
            List<FichaTecnicaGetDTO> fichas = fichaTecnicaService.buscarPorCustoTotal(custoTotal);
            model.addAttribute("fichas", fichas);
            return "Ficha/List";
        }

    @GetMapping("/por-nome")
    public String buscarPorNomePreparacao(@RequestParam String nome, Model model) {
        List<FichaTecnicaGetDTO> fichas = fichaTecnicaService.buscarPorNomePreparacao(nome);
        model.addAttribute("fichas", fichas);
        return "Ficha/List";
    }

    @GetMapping("/por-categoria")
    public String buscarPorCategoriaPreparacao(
            @RequestParam(name = "categoria") String nomeCategoria,
            Model model) {
        List<FichaTecnicaGetDTO> fichas = fichaTecnicaService.buscarPorCategoriaPreparacao(nomeCategoria);
        model.addAttribute("fichas", fichas);
        return "Ficha/List";
    }


    @GetMapping("/por-rendimento")
    public String buscarPorRendimento(@RequestParam BigDecimal rendimento, Model model) {
        List<FichaTecnicaGetDTO> fichas = fichaTecnicaService.buscarPorRendimentoPreparacao(rendimento);
        model.addAttribute("fichas", fichas);
        return "Ficha/List";
    }

    @GetMapping("/por-numero")
    public String buscarPorNumero(@RequestParam Integer numero, Model model) {
        List<FichaTecnicaGetDTO> fichas = fichaTecnicaService.buscarPorNumeroPreparacao(numero);
        model.addAttribute("fichas", fichas);
        return "Ficha/List";
    }

    @GetMapping("/por-vtc")
    public String buscarPorVtc(@RequestParam BigDecimal vtc, Model model) {
        List<FichaTecnicaGetDTO> fichas = fichaTecnicaService.buscarPorVtcPerfilNutricional(vtc);
        model.addAttribute("fichas", fichas);
        return "Ficha/List";
    }

    @GetMapping("/por-gramas-ptn")
    public String buscarPorGramasPTN(@RequestParam BigDecimal gramasPTN, Model model) {
        List<FichaTecnicaGetDTO> fichas = fichaTecnicaService.buscarPorGramasPTNPerfilNutricional(gramasPTN);
        model.addAttribute("fichas", fichas);
        return "Ficha/List";
    }

    @GetMapping("/por-gramas-cho")
    public String buscarPorGramasCHO(@RequestParam BigDecimal gramasCHO, Model model) {
        List<FichaTecnicaGetDTO> fichas = fichaTecnicaService.buscarPorGramasCHOPerfilNutricional(gramasCHO);
        model.addAttribute("fichas", fichas);
        return "Ficha/List";
    }

    @GetMapping("/por-gramas-lip")
    public String buscarPorGramasLIP(@RequestParam BigDecimal gramasLIP, Model model) {
        List<FichaTecnicaGetDTO> fichas = fichaTecnicaService.buscarPorGramasLIPPerfilNutricional(gramasLIP);
        model.addAttribute("fichas", fichas);
        return "Ficha/List";
    }

    @GetMapping("/por-gramas-sodio")
    public String buscarPorGramasSodio(@RequestParam BigDecimal gramasSodio, Model model) {
        List<FichaTecnicaGetDTO> fichas = fichaTecnicaService.buscarPorGramasSodioPerfilNutricional(gramasSodio);
        model.addAttribute("fichas", fichas);
        return "Ficha/List";
    }

    @GetMapping("/por-gramas-saturada")
    public String buscarPorGramasSaturada(@RequestParam BigDecimal gramasSaturada, Model model) {
        List<FichaTecnicaGetDTO> fichas = fichaTecnicaService.buscarPorGramasSaturadaPerfilNutricional(gramasSaturada);
        model.addAttribute("fichas", fichas);
        return "Ficha/List";
    }

    @GetMapping("/{id}")
        public String mostrarFichaPorId(@PathVariable Long id, Model model) {
            try {
                FichaTecnicaGetDTO ficha = fichaTecnicaService.getFichaById(id);
                model.addAttribute("ficha", ficha);
                return "Ficha/Detail";
            } catch (EntityNotFoundException e) {
                return "redirect:/fichas?error=Ficha não encontrada";
            }
        }

    @PostMapping("/toggle-status/{id}")
    public String toggleStatus(@PathVariable Long id) {
        fichaTecnicaService.atualizaStatus(id);
        return "redirect:/fichas/" + id;
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicao(@PathVariable Long id, Model model) {
        List<IngredienteGetDTO> ingredientesDisponiveis = ingredienteService.buscarPorStatus(Status.ATIVA);
        PerfilNutricionalGetDTO perfil = perfilService.getPerfilNutricionalById(id);
        FichaTecnicaGetDTO fichas = fichaTecnicaService.getFichaById(id);
        model.addAttribute("ingredientesDisponiveis", ingredientesDisponiveis);
        model.addAttribute("perfil", perfil);
        model.addAttribute("ficha", fichas);
        return "Ficha/Form";
    }

    @PostMapping("/editar/{id}")
    public String updateFichaTecnica(
            @PathVariable Long id,
            @ModelAttribute("ficha") FichaTecnicaUpdateDTO dto
    ) {
        FichaTecnicaGetDTO updatedFicha = fichaTecnicaService.updateFichaTecnica(id, dto);
        return "redirect:/fichas";
    }


    @GetMapping("/nova")
        public String mostrarFormularioNovaFicha(Model model) {
        List<IngredienteGetDTO> ingredientesDisponiveis = ingredienteService.buscarPorStatus(Status.ATIVA);

            model.addAttribute("categorias", Categoria.values());
            model.addAttribute("ingredientesDisponiveis", ingredientesDisponiveis);

            model.addAttribute("ficha", new FichaTecnicaCreateDTO(null, null, null, "", null,
                    null, ATIVA,null,null, new PreparacaoCreateDTO(null,"", null, "", "","",
                            null, null, null, null, null), new ArrayList<>(),
                    new PerfilNutricionalCreateDTO(null, null, null, null, null,
                            null, null, null, null, null, null,
                            null, null
                    )
            ));

            return "Ficha/Form";
        }

    @PostMapping
    public String salvarFichaTecnica(@ModelAttribute FichaTecnicaCreateDTO fichaTecnicaDTO) {
        FichaTecnicaGetDTO fichaSalva = fichaTecnicaService.createFichaTecnica(fichaTecnicaDTO);
        return "redirect:/fichas";
    }
}
