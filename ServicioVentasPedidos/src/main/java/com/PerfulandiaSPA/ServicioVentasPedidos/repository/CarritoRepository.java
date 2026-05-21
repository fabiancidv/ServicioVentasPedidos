package com.PerfulandiaSPA.ServicioVentasPedidos.repository;

import com.PerfulandiaSPA.ServicioVentasPedidos.model.Carrito;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CarritoRepository extends JpaRepository<Carrito, Long>{

    Optional<Carrito> findByClienteId(Long clienteId);
}
