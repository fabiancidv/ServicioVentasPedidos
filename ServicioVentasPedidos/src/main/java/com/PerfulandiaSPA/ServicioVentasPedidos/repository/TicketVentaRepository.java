package com.PerfulandiaSPA.ServicioVentasPedidos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.PerfulandiaSPA.ServicioVentasPedidos.model.TicketVenta;

public interface TicketVentaRepository extends JpaRepository<TicketVenta, Long>{
    
}
