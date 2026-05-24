package com.PerfulandiaSPA.ServicioVentasPedidos.controller;

import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.PerfulandiaSPA.ServicioVentasPedidos.model.EstadoPedido;
import com.PerfulandiaSPA.ServicioVentasPedidos.model.MetodoPago;
import com.PerfulandiaSPA.ServicioVentasPedidos.model.Pedido;
import com.PerfulandiaSPA.ServicioVentasPedidos.model.TipoEnvio;
import com.PerfulandiaSPA.ServicioVentasPedidos.service.VentasPedidoService;

@RestController
@RequestMapping("api/v1/pedidos")
public class PedidoController {
    @Autowired
    VentasPedidoService ventasPedidoService;

    @PostMapping("{carritoId}/{cupon}/{metodoPago}/{tipo}/{direccion}")
    ResponseEntity<?> postPedido(@PathVariable Long carritoId, @PathVariable String cupon, @PathVariable MetodoPago metodoPago, @PathVariable TipoEnvio tipo, @PathVariable String direccion){
        Pedido pedido = ventasPedidoService.realizarPedido(carritoId, cupon, metodoPago, tipo, direccion);
        if (pedido != null){
            return ResponseEntity.ok(pedido);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Carrito no encontrado");
    }

    @PostMapping("{id}")
    ResponseEntity<?> postRegistro(@PathVariable Long id){
        Pedido pedido = ventasPedidoService.registrarVenta(id);
        if (pedido != null){
            return ResponseEntity.ok(pedido);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pedido no encontrado");
    }

    @GetMapping("estado/{pedidoId}")
    ResponseEntity<?> getEstadoPedido(@PathVariable Long pedidoId){
        EstadoPedido estado = ventasPedidoService.consultarEstado(pedidoId);
        if (estado != null){
            return ResponseEntity.ok(estado);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pedido no encontrado");
    }

    @GetMapping("historial/{clienteId}")
    ResponseEntity<?> getHistorial(@PathVariable Long clienteId){
        List<Pedido> historial = ventasPedidoService.obtenerHistorialCompras(clienteId);
        return ResponseEntity.ok(historial);
    }
}
