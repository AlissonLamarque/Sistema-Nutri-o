package com.example.sistemanutricao.controller;

import com.example.sistemanutricao.model.Status;
import com.example.sistemanutricao.record.FichaTecnicaDTO.FichaTecnicaGetDTO;
import com.example.sistemanutricao.record.FichaTecnicaDTO.FichaTecnicaUpdateDTO;
import com.example.sistemanutricao.record.IngredienteDTO.IngredienteGetDTO;
import com.example.sistemanutricao.record.PerfilNutricionalDTO.PerfilNutricionalGetDTO;
import com.example.sistemanutricao.record.RefeicaoDTO.FichaTecnicaRefeicaoDTO;
import com.example.sistemanutricao.record.RefeicaoDTO.RefeicaoDTO;
import com.example.sistemanutricao.record.RefeicaoDTO.RefeicaoResponseDTO;
import com.example.sistemanutricao.service.FichaTecnicaService;
import com.example.sistemanutricao.service.RefeicaoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

import static com.example.sistemanutricao.model.Status.ATIVA;

@Controller
@RequestMapping("/refeicoes")
public class RefeicaoController {

    private final RefeicaoService refeicaoService;
    private final FichaTecnicaService fichaTecnicaService;

    public RefeicaoController(RefeicaoService refeicaoService,
                                  FichaTecnicaService fichaTecnicaService) {
        this.refeicaoService = refeicaoService;
        this.fichaTecnicaService = fichaTecnicaService;
    }

    @GetMapping
    public String listarRefeicoes(Model model) {
        model.addAttribute("refeicoes", refeicaoService.buscarPorStatus(ATIVA));
        model.addAttribute("statusAtual", ATIVA);
        return "Refeicao/List";
    }

    @GetMapping("/por-status")
    public String buscarPorStatus(
            @RequestParam Status status,
            Model model) {
        List<RefeicaoResponseDTO> refeicoes = refeicaoService.buscarPorStatus(status);
        model.addAttribute("refeicoes", refeicoes);
        model.addAttribute("statusAtual", status);
        return "Refeicao/List";
    }

    // Método para pesquisa por nome
    @GetMapping("/pesquisar-por-nome")
    public String pesquisarPorNome(
            @RequestParam("nome") String nome,
            @RequestParam(value = "status", required = false) Status status,
            Model model) {

        List<RefeicaoResponseDTO> refeicoes = refeicaoService.buscarPorNome(nome);

        model.addAttribute("refeicoes", refeicoes);
        model.addAttribute("statusAtual", status != null ? status : Status.ATIVA);
        model.addAttribute("termoPesquisa", nome);

        return "Refeicao/List";
    }

    @PostMapping("/toggle-status/{id}")
    public String toggleStatus(@PathVariable Long id) {
        refeicaoService.atualizaStatus(id);
        return "redirect:/refeicoes";
    }

    @GetMapping("/pesquisar")
    public String pesquisarPorNome(
            @RequestParam("nome") String nome,
            Model model) {

        List<RefeicaoResponseDTO> resultados = refeicaoService.buscarPorNome(nome);
        model.addAttribute("refeicoes", resultados);
        model.addAttribute("statusAtual", null);
        model.addAttribute("termoBusca", nome);
        return "Refeicao/List";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicao(@PathVariable Long id, Model model) {
        RefeicaoResponseDTO refeicaoResponse = refeicaoService.buscarPorId(id);
        List<FichaTecnicaRefeicaoDTO> fichas = fichaTecnicaService.listarResumo();

        model.addAttribute("refeicaoDTO", new RefeicaoDTO(
                refeicaoResponse.id(),
                refeicaoResponse.nome(),
                refeicaoResponse.kcalTotal(),
                refeicaoResponse.status(),
                refeicaoResponse.fichasTecnicas().stream().map(FichaTecnicaRefeicaoDTO::id).toList()
        ));
        model.addAttribute("fichasTecnicasRefeicao", refeicaoResponse.fichasTecnicas());
        model.addAttribute("fichasTecnicas", fichas);
        return "Refeicao/Form";
    }

    @PostMapping("/editar/{id}")
    public String atualizarRefeicao(@PathVariable Long id, @ModelAttribute RefeicaoDTO dto) {
        refeicaoService.atualizar(id, dto);
        return "redirect:/refeicoes";
    }


    @GetMapping("/novo")
    public String mostrarFormularioCriacao(Model model) {
        List<FichaTecnicaRefeicaoDTO> fichas = fichaTecnicaService.listarResumo();
        RefeicaoDTO refeicaoDTO = new RefeicaoDTO(null, null, "0", Status.ATIVA, new ArrayList<>());
        model.addAttribute("refeicaoDTO", refeicaoDTO);
        model.addAttribute("fichasTecnicas", fichas);
        model.addAttribute("fichasTecnicasRefeicao", new ArrayList<>());
        return "Refeicao/Form";
    }

    @PostMapping("/novo")
    public String criarRefeicao(

            @ModelAttribute RefeicaoDTO dto
    ) {
        refeicaoService.criar(dto);
        return "redirect:/refeicoes";
    }
}