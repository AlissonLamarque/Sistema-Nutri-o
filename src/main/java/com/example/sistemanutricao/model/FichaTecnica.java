package com.example.sistemanutricao.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Entity
public class FichaTecnica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal custoPerCapita;

    private BigDecimal custoTotal;

    private Integer numeroPorcoes;

    private BigDecimal pesoPorcao;

    private String medidaCaseira;

    private Status status;

    @Enumerated(EnumType.STRING)
    private StatusCriacao statusCriacao;

    @OneToOne
    @JoinColumn(name = "preparacao_id", nullable = false)
    private Preparacao preparacao;

    @OneToOne
    @JoinColumn(name = "perfil_nutricional_id", nullable = false)
    private PerfilNutricional perfilNutricional;

    @OneToMany(mappedBy = "fichaTecnica", cascade = CascadeType.ALL)
    private List<IngredientesPorFicha> ingredientesPorFicha;

    public FichaTecnica() {
    }

    public FichaTecnica(Long id, BigDecimal custoPerCapita, BigDecimal custoTotal, Integer numeroPorcoes, BigDecimal pesoPorcao,
                        String medidaCaseira, Status status, StatusCriacao statusCriacao, Preparacao preparacao, PerfilNutricional perfilNutricional,
                        List<IngredientesPorFicha> ingredientesPorFicha) {
        this.id = id;
        this.custoPerCapita = custoPerCapita;
        this.custoTotal = custoTotal;
        this.numeroPorcoes = numeroPorcoes;
        this.pesoPorcao = pesoPorcao;
        this.medidaCaseira = medidaCaseira;
        this.status = status;
        this.statusCriacao = statusCriacao;
        this.preparacao = preparacao;
        this.perfilNutricional = perfilNutricional;
        this.ingredientesPorFicha = ingredientesPorFicha;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getCustoPerCapita() {
        return custoPerCapita;
    }

    public void setCustoPerCapita(BigDecimal custoPerCapita) {
        this.custoPerCapita = custoPerCapita;
    }

    public BigDecimal getCustoTotal() {
        return custoTotal;
    }

    public void setCustoTotal(BigDecimal custoTotal) {
        this.custoTotal = custoTotal;
    }

    public Integer getNumeroDePorcoes() {
        return numeroPorcoes;
    }

    public void setNumeroDePorcoes(Integer numeroDePorcoes) {
        this.numeroPorcoes = numeroDePorcoes;
    }

    public BigDecimal getPesoPorcao() {
        return pesoPorcao;
    }

    public void setPesoPorcao(BigDecimal pesoPorcao) {
        this.pesoPorcao = pesoPorcao;
    }

    public String getMedidaCaseira() {
        return medidaCaseira;
    }

    public void setMedidaCaseira(String medidaCaseria) {
        this.medidaCaseira = medidaCaseria;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Preparacao getPreparacao() {
        return preparacao;
    }

    public void setPreparacao(Preparacao preparacao) {
        this.preparacao = preparacao;
    }

    public PerfilNutricional getPerfilNutricional() {
        return perfilNutricional;
    }

    public void setPerfilNutricional(PerfilNutricional perfilNutricional) {
        this.perfilNutricional = perfilNutricional;
    }

    public List<IngredientesPorFicha> getIngredientesPorFicha() {
        return ingredientesPorFicha;
    }

    public void setIngredientesPorFicha(List<IngredientesPorFicha> ingredientesPorFicha) {
        this.ingredientesPorFicha = ingredientesPorFicha;
    }

    public Integer getNumeroPorcoes() {
        return numeroPorcoes;
    }

    public void setNumeroPorcoes(Integer numeroPorcoes) {
        this.numeroPorcoes = numeroPorcoes;
    }

    public StatusCriacao getStatusCriacao() {
        return statusCriacao;
    }

    public void setStatusCriacao(StatusCriacao statusCriacao) {
        this.statusCriacao = statusCriacao;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        FichaTecnica that = (FichaTecnica) o;
        return Objects.equals(id, that.id) && Objects.equals(custoPerCapita, that.custoPerCapita) && Objects.equals(custoTotal, that.custoTotal) && Objects.equals(numeroPorcoes, that.numeroPorcoes) && Objects.equals(pesoPorcao, that.pesoPorcao) && Objects.equals(medidaCaseira, that.medidaCaseira) && status == that.status && statusCriacao == that.statusCriacao && Objects.equals(preparacao, that.preparacao) && Objects.equals(perfilNutricional, that.perfilNutricional) && Objects.equals(ingredientesPorFicha, that.ingredientesPorFicha);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, custoPerCapita, custoTotal, numeroPorcoes, pesoPorcao, medidaCaseira, status, statusCriacao, preparacao, perfilNutricional, ingredientesPorFicha);
    }

    @Override
    public String toString() {
        return "FichaTecnica{" +
                "id=" + id +
                ", custoPerCapita=" + custoPerCapita +
                ", custoTotal=" + custoTotal +
                ", numeroPorcoes=" + numeroPorcoes +
                ", pesoPorcao=" + pesoPorcao +
                ", medidaCaseira='" + medidaCaseira + '\'' +
                ", status=" + status +
                ", statusCriacao=" + statusCriacao +
                ", preparacao=" + preparacao +
                ", perfilNutricional=" + perfilNutricional +
                ", ingredientesPorFicha=" + ingredientesPorFicha +
                '}';
    }
}
