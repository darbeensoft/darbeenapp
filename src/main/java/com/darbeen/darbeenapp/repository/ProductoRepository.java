package com.darbeen.darbeenapp.repository;

import com.darbeen.darbeenapp.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
}
