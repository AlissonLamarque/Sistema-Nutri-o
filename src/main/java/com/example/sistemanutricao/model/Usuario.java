package com.example.sistemanutricao.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String email;

    private String senha;

    private Cargo cargo;

    @ManyToOne
    @JoinColumn(name = "estabelecimento_id", nullable = false)
    private Estabelecimento estabelecimento;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Ingrediente> ingrediente;

    public Usuario() {
    }

    public Usuario(Long id, String username, String email, String senha, Cargo cargo, Estabelecimento estabelecimento, List<Ingrediente> ingrediente) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.senha = senha;
        this.cargo = cargo;
        this.estabelecimento = estabelecimento;
        this.ingrediente = ingrediente;
    }

    public List<Ingrediente> getIngrediente() {
        return ingrediente;
    }

    public void setIngrediente(List<Ingrediente> ingrediente) {
        this.ingrediente = ingrediente;
    }

    public Estabelecimento getEstabelecimento() {
        return estabelecimento;
    }

    public void setEstabelecimento(Estabelecimento estabelecimento) {
        this.estabelecimento = estabelecimento;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(id, usuario.id) && Objects.equals(username, usuario.username) && Objects.equals(email, usuario.email) && Objects.equals(senha, usuario.senha) && cargo == usuario.cargo && Objects.equals(estabelecimento, usuario.estabelecimento) && Objects.equals(ingrediente, usuario.ingrediente);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, email, senha, cargo, estabelecimento, ingrediente);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                ", cargo=" + cargo +
                ", estabelecimento=" + estabelecimento +
                ", ingrediente=" + ingrediente +
                '}';
    }
}
