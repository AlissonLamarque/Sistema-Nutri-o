package com.example.sistemanutricao.repository;

import com.example.sistemanutricao.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
