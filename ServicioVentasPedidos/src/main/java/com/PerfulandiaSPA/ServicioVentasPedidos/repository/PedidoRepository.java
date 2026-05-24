package com.PerfulandiaSPA.ServicioVentasPedidos.repository;

import com.PerfulandiaSPA.ServicioVentasPedidos.model.Pedido;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long>{
    List<Pedido> findByClienteId(Long clienteId);
}
