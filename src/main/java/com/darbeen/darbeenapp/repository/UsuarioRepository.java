package com.darbeen.darbeenapp.repository;

import com.darbeen.darbeenapp.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
