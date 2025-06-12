package com.example.sistemanutricao.service;

import com.example.sistemanutricao.model.*;
import com.example.sistemanutricao.record.FichaTecnicaDTO.FichaTecnicaCreateDTO;
import com.example.sistemanutricao.record.FichaTecnicaDTO.FichaTecnicaGetDTO;
import com.example.sistemanutricao.record.FichaTecnicaDTO.FichaTecnicaUpdateDTO;
import com.example.sistemanutricao.record.IngredientesPorFichaDTO.IngredientePorFichaGetDTO;
import com.example.sistemanutricao.record.IngredientesPorFichaDTO.IngredientePorFichaDTO;
import com.example.sistemanutricao.record.PerfilNutricionalDTO.PerfilNutricionalGetDTO;
import com.example.sistemanutricao.record.PreparacaoDTO.PreparacaoGetDTO;
import com.example.sistemanutricao.record.RefeicaoDTO.FichaTecnicaRefeicaoDTO;
import com.example.sistemanutricao.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FichaTecnicaService {

    private final FichaTecnicaRepository fichaRepository;
    private final PreparacaoService preparacaoService;
    private final PerfilNutricionalService perfilService;
    private final IngredientesPorFichaService ipfService;

    public FichaTecnicaService(FichaTecnicaRepository fichaRepository,
                               PreparacaoService preparacaoService,
                               PerfilNutricionalService perfilService,
                               IngredientesPorFichaService ipfService) {
        this.fichaRepository = fichaRepository;
        this.preparacaoService = preparacaoService;
        this.perfilService = perfilService;
        this.ipfService = ipfService;
    }

    public FichaTecnicaGetDTO createFichaTecnica(FichaTecnicaCreateDTO dto) {
        PreparacaoGetDTO preparacaoDTO = preparacaoService.createPreparacao(dto.preparacao());

        PerfilNutricionalGetDTO perfilDTO = perfilService.createPerfilNutricional(dto.perfilNutricional());
        System.out.println(">>>> statusCriacao recebido: " + dto.statusCriacao());
        FichaTecnica ficha = new FichaTecnica();
        ficha.setCustoPerCapita(dto.custoPerCapita());
        ficha.setCustoTotal(dto.custoTotal());
        ficha.setNumeroDePorcoes(dto.numeroPorcoes());
        ficha.setPesoPorcao(dto.pesoPorcao());
        ficha.setMedidaCaseira(dto.medidaCaseira());
        ficha.setStatus(dto.status());
        ficha.setStatusCriacao(dto.statusCriacao());
        ficha.setPreparacao(
                new Preparacao(preparacaoDTO.id())
        );
        ficha.setPerfilNutricional(
                new PerfilNutricional(perfilDTO.id())
        );
        FichaTecnica fichaSalva = fichaRepository.save(ficha);

        List<IngredientePorFichaGetDTO> ingredientesDTOs =
                ipfService.createIngredientesPorFicha(dto.ingredientes(), fichaSalva, perfilDTO.id());

        return convertToDto(fichaSalva, ingredientesDTOs);
    }

    public FichaTecnicaGetDTO updateFichaTecnica(Long id, FichaTecnicaUpdateDTO dto) {
        FichaTecnica fichaExistente = fichaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ficha Técnica não encontrada com ID: " + id));

        Preparacao preparacaoAtualizada = preparacaoService.updatePreparacao(dto.preparacao());
        PerfilNutricional perfilAtualizado = perfilService.updatePerfilNutricional(
                fichaExistente.getPerfilNutricional().getId(),
                dto.perfilNutricional());

        fichaExistente.setCustoPerCapita(dto.custoPerCapita());
        fichaExistente.setCustoTotal(dto.custoTotal());
        fichaExistente.setNumeroDePorcoes(dto.numeroPorcoes());
        fichaExistente.setPesoPorcao(dto.pesoPorcao());
        fichaExistente.setMedidaCaseira(dto.medidaCaseira());
        fichaExistente.setStatus(dto.status());
        fichaExistente.setStatusCriacao(dto.statusCriacao());
        fichaExistente.setPreparacao(preparacaoAtualizada);
        fichaExistente.setPerfilNutricional(perfilAtualizado);

        FichaTecnica fichaAtualizada = fichaRepository.save(fichaExistente);

        List<IngredientePorFichaGetDTO> ingredientesAtualizados = new ArrayList<>();

        for (IngredientePorFichaDTO ingredienteDTO : dto.ingredientes()) {
            if (ingredienteDTO.id() != null) {
                ingredientesAtualizados.add(ipfService.updateIngredientePorFicha(ingredienteDTO, ingredienteDTO.id()));
            }
        }

        List<IngredientePorFichaDTO> novosIngredientes = dto.ingredientes().stream()
                .filter(ing -> ing.id() == null)
                .toList();

        if (!novosIngredientes.isEmpty()) {
            ingredientesAtualizados.addAll(ipfService.createIngredientesPorFicha(dto.ingredientes(), fichaAtualizada, perfilAtualizado.getId()));
        }

        return convertToDto(fichaAtualizada, ingredientesAtualizados);
    }

    public List<FichaTecnicaRefeicaoDTO> listarResumo() {
        return fichaRepository.findAll().stream()
                .map(f -> new FichaTecnicaRefeicaoDTO(
                        f.getId(),
                        f.getPreparacao().getNome(),
                        f.getPerfilNutricional().getVtc()
                ))
                .collect(Collectors.toList());
    }

    public void atualizaStatus(Long id) {
        FichaTecnica ficha = fichaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ficha não encontrada"));

        ficha.setStatus(
                ficha.getStatus() == Status.ATIVA ?
                        Status.INATIVA :
                        Status.ATIVA
        );

        fichaRepository.save(ficha);
    }

    public FichaTecnicaGetDTO getFichaById(Long id) {
        FichaTecnica ficha = fichaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ficha técnica não encontrada com ID: " + id));
        return convertToDto(
                ficha,
                ipfService
                        .listarIngredientesPorFichaId(ficha.getId())
        );
    }

    public List<FichaTecnicaGetDTO> buscarPorCustoPerCapita(BigDecimal custoPerCapita) {
        return fichaRepository.findByCustoPerCapita(custoPerCapita).stream()
                .map(ficha -> convertToDto(
                        ficha,
                        ipfService.getAllIngredientesPorFicha()
                ))
                .toList();
    }

    public List<FichaTecnicaGetDTO> buscarPorCustoTotal(BigDecimal custoTotal) {
        return fichaRepository.findByCustoTotal(custoTotal).stream()
                .map(ficha -> convertToDto(
                        ficha,
                        ipfService.getAllIngredientesPorFicha()
                ))
                .toList();
    }

    public List<FichaTecnicaGetDTO> buscarPorStatus(Status status) {
        return fichaRepository.findByStatus(status).stream()
                .map(ficha -> convertToDto(
                        ficha,
                        ipfService.getAllIngredientesPorFicha()
                ))
                .toList();
    }

    public List<FichaTecnicaGetDTO> buscarPorStatusCriacao(StatusCriacao statusCriacao) {
        return fichaRepository.findByStatusCriacao(statusCriacao).stream()
                .map(ficha -> convertToDto(
                        ficha,
                        ipfService.getAllIngredientesPorFicha()
                ))
                .toList();
    }

    public List<FichaTecnicaGetDTO> buscarPorNomePreparacao(String nome) {
        return fichaRepository.findByPreparacaoNome(nome).stream()
                .map(ficha -> convertToDto(
                        ficha,
                        ipfService.getAllIngredientesPorFicha()
                ))
                .toList();
    }

    public List<FichaTecnicaGetDTO> buscarPorCategoriaPreparacao(String nome) {
        return fichaRepository.findByPreparacaoCategoriaNome(nome).stream()
        .map(ficha -> convertToDto(
                ficha,
                ipfService.getAllIngredientesPorFicha()
        ))
                .toList();
    }

    public List<FichaTecnicaGetDTO> buscarPorRendimentoPreparacao(BigDecimal rendimento) {
        return fichaRepository.findByPreparacaoRendimento(rendimento).stream()
                .map(ficha -> convertToDto(
                        ficha,
                        ipfService.getAllIngredientesPorFicha()
                ))
                .toList();
    }

    public List<FichaTecnicaGetDTO> buscarPorNumeroPreparacao(Integer numero) {
        return fichaRepository.findByPreparacaoNumero(numero).stream()
                .map(ficha -> convertToDto(
                        ficha,
                        ipfService.getAllIngredientesPorFicha()
                ))
                .toList();
    }

    public List<FichaTecnicaGetDTO> buscarPorVtcPerfilNutricional(BigDecimal vtc) {
        return fichaRepository.findByPerfilNutricionalVtc(vtc).stream()
                .map(ficha -> convertToDto(
                        ficha,
                        ipfService.getAllIngredientesPorFicha()
                ))
                .toList();
    }

    public List<FichaTecnicaGetDTO> buscarPorGramasPTNPerfilNutricional(BigDecimal gramasPTN) {
        return fichaRepository.findByPerfilNutricionalGramasPTN(gramasPTN).stream()
                .map(ficha -> convertToDto(
                        ficha,
                        ipfService.getAllIngredientesPorFicha()
                ))
                .toList();
    }

    public List<FichaTecnicaGetDTO> buscarPorGramasCHOPerfilNutricional(BigDecimal gramasCHO) {
        return fichaRepository.findByPerfilNutricionalGramasCHO(gramasCHO).stream()
                .map(ficha -> convertToDto(
                        ficha,
                        ipfService.getAllIngredientesPorFicha()
                ))
                .toList();
    }

    public List<FichaTecnicaGetDTO> buscarPorGramasLIPPerfilNutricional(BigDecimal gramasLIP) {
        return fichaRepository.findByPerfilNutricionalGramasLIP(gramasLIP).stream()
                .map(ficha -> convertToDto(
                        ficha,
                        ipfService.getAllIngredientesPorFicha()
                ))
                .toList();
    }

    public List<FichaTecnicaGetDTO> buscarPorGramasSodioPerfilNutricional(BigDecimal gramasSodio) {
        return fichaRepository.findByPerfilNutricionalGramasSodio(gramasSodio).stream()
                .map(ficha -> convertToDto(
                        ficha,
                        ipfService.getAllIngredientesPorFicha()
                ))
                .toList();
    }

    public List<FichaTecnicaGetDTO> buscarPorGramasSaturadaPerfilNutricional(BigDecimal gramasSaturada) {
        return fichaRepository.findByPerfilNutricionalGramasSaturada(gramasSaturada).stream()
                .map(ficha -> convertToDto(
                        ficha,
                        ipfService.getAllIngredientesPorFicha()
                ))
                .toList();
    }

    private FichaTecnicaGetDTO convertToDto(FichaTecnica ficha,
                                                List<IngredientePorFichaGetDTO> ingredientes) {
            return new FichaTecnicaGetDTO(
                    ficha.getId(),
                    ficha.getCustoPerCapita(),
                    ficha.getCustoTotal(),
                    ficha.getMedidaCaseira(),
                    ficha.getNumeroDePorcoes(),
                    ficha.getPesoPorcao(),
                    ficha.getStatus(),
                    ficha.getStatusCriacao(),
                    preparacaoService.getPreparacaoById(ficha.getPreparacao().getId()),
                    ingredientes,
                    perfilService.getPerfilNutricionalById(ficha.getPerfilNutricional().getId())
            );
        }
}


