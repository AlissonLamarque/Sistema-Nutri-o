package com.example.sistemanutricao.service;

import com.example.sistemanutricao.model.Ingrediente;
import com.example.sistemanutricao.model.Status;
import com.example.sistemanutricao.model.Usuario;
import com.example.sistemanutricao.record.IngredienteDTO.IngredienteCreateDTO;
import com.example.sistemanutricao.record.IngredienteDTO.IngredienteGetDTO;
import com.example.sistemanutricao.record.IngredienteDTO.IngredienteUpdateDTO;
import com.example.sistemanutricao.repository.IngredienteRepository;
import com.example.sistemanutricao.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class IngredienteService {

    private final IngredienteRepository ingredienteRepository;
    private final UsuarioRepository usuarioRepository;

    public IngredienteService(IngredienteRepository ingredienteRepository, UsuarioRepository usuarioRepository) {
        this.ingredienteRepository = ingredienteRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public IngredienteGetDTO createIngrediente(IngredienteCreateDTO dto) {
        Ingrediente ing = new Ingrediente();
        ing.setNome(dto.nome());
        ing.setPtn(dto.ptn());
        ing.setCho(dto.cho());
        ing.setLip(dto.lip());
        ing.setStatus(dto.status());
        ing.setSodio(dto.sodio());
        ing.setGorduraSaturada(dto.gorduraSaturada());

        Usuario usuario = usuarioRepository.findById(dto.usuarioId())
                .orElseThrow(() -> new NoSuchElementException("Usuário não encontrado"));
        ing.setUsuario(usuario);

        Ingrediente ingredienteSalvo = ingredienteRepository.save(ing);
        return convertToDto(ingredienteSalvo);
    }

    public IngredienteGetDTO updateIngrediente(IngredienteUpdateDTO dto, IngredienteGetDTO dtoId) {
        Ingrediente ing = ingredienteRepository.findById(dtoId.id())
                .orElseThrow(() -> new NoSuchElementException("Ingrediente não encontrada"));
            ing.setNome(dto.nome());
            ing.setPtn(dto.ptn());
            ing.setCho(dto.cho());
            ing.setLip(dto.lip());
            ing.setStatus(dto.status());
            ing.setSodio(dto.sodio());
            ing.setGorduraSaturada(dto.gorduraSaturada());
        Ingrediente ingredienteSalvo = ingredienteRepository.save(ing);

        return convertToDto(ingredienteSalvo);
    }

    public IngredienteGetDTO getIngredienteById(Long id) {
        Ingrediente ing = ingredienteRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Ingrediente não encontrada"));
        return convertToDto(ing);
    }

    public List<IngredienteGetDTO> buscarTodosIngredientes() {
        return ingredienteRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .toList();
    }

    public List<IngredienteGetDTO> buscarPorStatus(Status status) {
        return ingredienteRepository.findByStatus(status).stream()
                .map(this::convertToDto).toList();
    }

    public List<IngredienteGetDTO> buscarPorStatusEUsuario(Status status, Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);
        if (usuario == null) {
            return new ArrayList<>();
        }

        List<Ingrediente> ingredientes = ingredienteRepository
                .findByStatusAndUsuario(status, usuario);
        return ingredientes.stream()
                .map(this::convertToDto)
                .toList();
    }


    public List<Ingrediente> buscarIngredientesDoUsuario1() {
        return usuarioRepository.findById(1L)
                .map(ingredienteRepository::findByUsuario)
                .orElseThrow(() -> new RuntimeException("Usuário com id=1 não encontrado"));
    }

    public void atualizaStatus(Long id) {
        Ingrediente ingrediente = ingredienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ingrediente não encontrado"));

        ingrediente.setStatus(
                ingrediente.getStatus() == Status.ATIVA ?
                        Status.INATIVA :
                        Status.ATIVA
        );

        ingredienteRepository.save(ingrediente);
    }

    public List<IngredienteGetDTO> buscarPorNome(String nome) {

        return ingredienteRepository.findByNome(nome).stream()
                .map(this::convertToDto).toList();
    }

    public List<IngredienteGetDTO> buscarPorPTN(BigDecimal ptn) {

        return ingredienteRepository.findByPtn(ptn).stream()
                .map(this::convertToDto).toList();
    }

    public List<IngredienteGetDTO> buscarPorCHO(BigDecimal cho) {

        return ingredienteRepository.findByCho(cho).stream()
                .map(this::convertToDto).toList();
    }

    public List<IngredienteGetDTO> buscarPorLIP(BigDecimal lip) {

        return ingredienteRepository.findByLip(lip).stream()
                .map(this::convertToDto).toList();
    }

    public List<IngredienteGetDTO> buscarPorSodio(BigDecimal sodio) {

        return ingredienteRepository.findBySodio(sodio).stream()
                .map(this::convertToDto).toList();
    }

    public List<IngredienteGetDTO> buscarPorGorduraSaturada(BigDecimal gorduraSaturada) {
        return ingredienteRepository.findByGorduraSaturada(gorduraSaturada).stream()
                .map(this::convertToDto).toList();
    }

    private IngredienteGetDTO convertToDto(Ingrediente ingre) {
        return new IngredienteGetDTO(
                ingre.getId(), ingre.getNome(), ingre.getPtn(),
                ingre.getCho(), ingre.getLip(), ingre.getStatus(),
                ingre.getSodio(), ingre.getGorduraSaturada(), ingre.getUsuario().getId());
    }
}

