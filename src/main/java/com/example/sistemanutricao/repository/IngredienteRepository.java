package com.example.sistemanutricao.repository;

import com.example.sistemanutricao.model.FichaTecnica;
import com.example.sistemanutricao.model.Ingrediente;
import com.example.sistemanutricao.model.Status;
import com.example.sistemanutricao.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface IngredienteRepository extends JpaRepository<Ingrediente, Long> {

    List<Ingrediente> findByNome(String nome);

    List<Ingrediente> findByPtn(BigDecimal ptn);

    List<Ingrediente> findByCho(BigDecimal cho);

    List<Ingrediente> findByLip(BigDecimal lip);

    List<Ingrediente> findBySodio(BigDecimal sodio);

    List<Ingrediente> findByGorduraSaturada(BigDecimal gorduraSaturada);

    List<Ingrediente> findByStatus(Status status);

    List<Ingrediente> findByUsuario(Usuario usuario);

    List<Ingrediente> findByStatusAndUsuario(Status status, Usuario usuario);


}