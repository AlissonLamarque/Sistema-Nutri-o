package com.example.sistemanutricao.repository;

import com.example.sistemanutricao.model.Categoria;
import com.example.sistemanutricao.model.FichaTecnica;
import com.example.sistemanutricao.model.Status;
import com.example.sistemanutricao.model.StatusCriacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public interface FichaTecnicaRepository extends JpaRepository<FichaTecnica, Long> {

    List<FichaTecnica> findByCustoPerCapita(BigDecimal custoPerCapita);

    List<FichaTecnica> findByCustoTotal(BigDecimal custoTotal);

    List<FichaTecnica> findByPreparacaoNome(String nome);

    @Query("SELECT f FROM FichaTecnica f WHERE f.preparacao.categoria = :categoria")
    List<FichaTecnica> findByPreparacaoCategoria(@Param("categoria") Categoria categoria);

    default List<FichaTecnica> findByPreparacaoCategoriaNome(String nomeCategoria) {
        Categoria categoria = Arrays.stream(Categoria.values())
                .filter(c -> c.getNome().equalsIgnoreCase(nomeCategoria))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Categoria inválida: " + nomeCategoria));

        return findByPreparacaoCategoria(categoria);
    }

    List<FichaTecnica> findByPreparacaoRendimento(BigDecimal rendimento);

    List<FichaTecnica> findByPreparacaoNumero(Integer numero);

    List<FichaTecnica> findByPerfilNutricionalVtc(BigDecimal vtc);

    List<FichaTecnica> findByPerfilNutricionalGramasPTN(BigDecimal gramasPTN);

    List<FichaTecnica> findByPerfilNutricionalGramasCHO(BigDecimal gramasCHO);

    List<FichaTecnica> findByPerfilNutricionalGramasLIP(BigDecimal gramasLIP);

    List<FichaTecnica> findByPerfilNutricionalGramasSodio(BigDecimal gramasSodio);

    List<FichaTecnica> findByPerfilNutricionalGramasSaturada(BigDecimal gramasSaturada);

    List<FichaTecnica> findByStatus(Status status);

    List<FichaTecnica> findByStatusCriacao(StatusCriacao statusCriacao);
}
