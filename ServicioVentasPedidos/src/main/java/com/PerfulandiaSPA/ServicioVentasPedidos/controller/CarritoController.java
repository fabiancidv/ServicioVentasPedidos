package com.PerfulandiaSPA.ServicioVentasPedidos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.PerfulandiaSPA.ServicioVentasPedidos.model.Carrito;
import com.PerfulandiaSPA.ServicioVentasPedidos.service.VentasPedidoService;

@RestController
@RequestMapping("api/v1/carritos")
public class CarritoController {
    @Autowired
    private VentasPedidoService ventasPedidoService;

    @PostMapping("{productoId}/{cantidad}/{clienteId}/{stockId}")
    public ResponseEntity<?> postCarrito(@PathVariable Long productoId, @PathVariable int cantidad, @PathVariable Long clienteId, @PathVariable Long stockId){
        Carrito productoAgregado = ventasPedidoService.agregarAlCarrito(productoId, cantidad, clienteId, stockId);
        return ResponseEntity.ok(productoAgregado);
    }
}
