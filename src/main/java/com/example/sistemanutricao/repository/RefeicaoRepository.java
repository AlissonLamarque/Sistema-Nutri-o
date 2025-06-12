package com.example.sistemanutricao.repository;

import com.example.sistemanutricao.model.FichaTecnica;
import com.example.sistemanutricao.model.Refeicao;
import com.example.sistemanutricao.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RefeicaoRepository extends JpaRepository<Refeicao, Long> {

    List<Refeicao> findByStatus(Status status);

    List<Refeicao> findByNome(String nome);
}