package com.example.sistemanutricao.controller;

import com.example.sistemanutricao.model.FichaTecnica;
import com.example.sistemanutricao.model.Ingrediente;
import com.example.sistemanutricao.model.Status;
import com.example.sistemanutricao.record.FichaTecnicaDTO.FichaTecnicaGetDTO;
import com.example.sistemanutricao.record.IngredienteDTO.IngredienteCreateDTO;
import com.example.sistemanutricao.record.IngredienteDTO.IngredienteGetDTO;
import com.example.sistemanutricao.record.IngredienteDTO.IngredienteUpdateDTO;
import com.example.sistemanutricao.service.IngredienteService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.List;

import static com.example.sistemanutricao.model.Status.ATIVA;

@Controller
@RequestMapping("/ingredientes")
public class IngredienteController {

    private final IngredienteService ingredienteService;

    public IngredienteController(IngredienteService ingredienteService) {
        this.ingredienteService = ingredienteService;
    }

    @GetMapping
    public String listarIngredientes(Model model,  String view) {
        Long usuarioId = 2L; // usuário fixo por enquanto
        List<IngredienteGetDTO> ingredientes = ingredienteService.buscarPorStatusEUsuario(ATIVA, usuarioId);
        model.addAttribute("ingredientes", ingredientes);
        model.addAttribute("statusAtual", ATIVA);
        model.addAttribute("view", view != null ? view : "meus");
        return "Ingrediente/List";
    }


    @GetMapping("/usuario1")
    public String listarIngredientesDoUsuario1(Model model, String view) {
        List<Ingrediente> ingredientes = ingredienteService.buscarIngredientesDoUsuario1();
        model.addAttribute("ingredientes", ingredientes);
        model.addAttribute("view", "taco");
        return "Ingrediente/List";
    }

    @GetMapping("/por-nome")
    public String buscarPorNome(@RequestParam String nome, Model model) {
        List<IngredienteGetDTO> ingredientes = ingredienteService.buscarPorNome(nome);
        model.addAttribute("ingredientes", ingredientes);
        return "Ingrediente/List";
    }

    @GetMapping("/por-ptn")
    public String buscarPorPTN(@RequestParam BigDecimal ptn, Model model) {
        List<IngredienteGetDTO> ingredientes = ingredienteService.buscarPorPTN(ptn);
        model.addAttribute("ingredientes", ingredientes);
        return "Ingrediente/List";
    }

    @GetMapping("/por-cho")
    public String buscarPorCHO(@RequestParam BigDecimal cho, Model model) {
        List<IngredienteGetDTO> ingredientes = ingredienteService.buscarPorCHO(cho);
        model.addAttribute("ingredientes", ingredientes);
        return "Ingrediente/List";
    }

    @GetMapping("/por-lip")
    public String buscarPorLIP(@RequestParam BigDecimal lip, Model model) {
        List<IngredienteGetDTO> ingredientes = ingredienteService.buscarPorLIP(lip);
        model.addAttribute("ingredientes", ingredientes);
        return "Ingrediente/List";
    }

    @GetMapping("/por-sodio")
    public String buscarPorSodio(@RequestParam BigDecimal sodio, Model model) {
        List<IngredienteGetDTO> ingredientes = ingredienteService.buscarPorSodio(sodio);
        model.addAttribute("ingredientes", ingredientes);
        return "Ingrediente/List";
    }

    @GetMapping("/por-gordura-saturada")
    public String buscarPorGorduraSaturada(@RequestParam BigDecimal gorduraSaturada, Model model) {
        List<IngredienteGetDTO> ingredientes = ingredienteService.buscarPorGorduraSaturada(gorduraSaturada);
        model.addAttribute("ingredientes", ingredientes);
        return "Ingrediente/List";
    }

    @GetMapping("/por-status")
    public String buscarPorStatus(
            @RequestParam Status status,
            Model model) {
        List<IngredienteGetDTO> ingredientes = ingredienteService.buscarPorStatus(status);
        model.addAttribute("ingredientes", ingredientes);
        model.addAttribute("statusAtual", status);
        return "Ingrediente/List";
    }

    @PostMapping("/atualiza-status/{id}")
    public String atualizaStatus(@PathVariable Long id) {
        ingredienteService.atualizaStatus(id);
        return "redirect:/ingredientes";
    }


    @GetMapping("/novo")
    public String mostrarFormularioCriacao(Model model) {
        model.addAttribute("ingrediente", new IngredienteGetDTO(null,"" , null,  null,  null, null,  null,  null, null));
        return "Ingrediente/Form";
    }

    @PostMapping("/novo")
    public String criarIngrediente(@ModelAttribute("ingrediente") IngredienteCreateDTO dto) {
        ingredienteService.createIngrediente(dto);
        return "redirect:/ingredientes";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicao(@PathVariable Long id, Model model) {
        IngredienteGetDTO ingrediente = ingredienteService.getIngredienteById(id);
        model.addAttribute("ingrediente", ingrediente);
        return "Ingrediente/Form";
    }

    @PostMapping("/editar/{id}")
    public String atualizarIngrediente(@PathVariable Long id,
                                       @ModelAttribute("ingrediente") IngredienteUpdateDTO dto) {
        IngredienteGetDTO dtoId = new IngredienteGetDTO(id, "", null, null, null, null, null, null, null);
        ingredienteService.updateIngrediente(dto, dtoId);
        return "redirect:/ingredientes";
    }
}