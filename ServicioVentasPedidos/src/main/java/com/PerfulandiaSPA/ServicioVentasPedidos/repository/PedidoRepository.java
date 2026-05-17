package com.PerfulandiaSPA.ServicioVentasPedidos.repository;

import com.PerfulandiaSPA.ServicioVentasPedidos.model.Pedido;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long>{
    
}
