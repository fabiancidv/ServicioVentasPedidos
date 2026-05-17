package com.PerfulandiaSPA.ServicioVentasPedidos.repository;

import com.PerfulandiaSPA.ServicioVentasPedidos.model.Carrito;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CarritoRepository extends JpaRepository<Carrito, Long>{
    
}
