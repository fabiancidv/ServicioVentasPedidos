package com.PerfulandiaSPA.ServicioVentasPedidos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.PerfulandiaSPA.ServicioVentasPedidos.dto.ProductoDTO;
import com.PerfulandiaSPA.ServicioVentasPedidos.dto.StockDTO;
import com.PerfulandiaSPA.ServicioVentasPedidos.model.Carrito;
import com.PerfulandiaSPA.ServicioVentasPedidos.model.EstadoPedido;
import com.PerfulandiaSPA.ServicioVentasPedidos.model.ItemsCarrito;
import com.PerfulandiaSPA.ServicioVentasPedidos.model.Pedido;
import com.PerfulandiaSPA.ServicioVentasPedidos.repository.CarritoRepository;
import com.PerfulandiaSPA.ServicioVentasPedidos.repository.PedidoRepository;

@Service
@Transactional
public class VentasPedidoService {
    @Autowired
    CarritoRepository carritoRepository;

    @Autowired
    PedidoRepository pedidoRepository;

    @Autowired
    RestTemplate restTemplate;

    public Carrito agregarAlCarrito(Long productoId, int cantidad, Long clienteId, Long stockId){

        
        String urlStock = "http://localhost:8081/api/v1/stocks/" + stockId;
        StockDTO stock = restTemplate.getForObject(urlStock, StockDTO.class);

        if (cantidad > stock.getCantidadDisponible()){
            return null;
        }

        String urlProducto = "http://localhost:8081/api/v1/productos/" + productoId;
        ProductoDTO producto = restTemplate.getForObject(urlProducto, ProductoDTO.class);

        Carrito carrito = carritoRepository.findByClienteId(clienteId).orElse(null);
        if (carrito == null){
            Carrito nuevoCarrito = new Carrito();
            nuevoCarrito.setClienteId(clienteId);
            nuevoCarrito.setTotalTemporal(0);
            nuevoCarrito.setItems(null);
            carrito = carritoRepository.save(nuevoCarrito);
        }

        boolean productoExiste = false;
        for (ItemsCarrito item : carrito.getItems()) {
            if (item.getProductoId().equals(carrito)){
                item.setCantidad(item.getCantidad() + cantidad);
                productoExiste = true;
                break;
            }
        }

        if (!productoExiste){
            ItemsCarrito nuevoItem = new ItemsCarrito();
            nuevoItem.setProductoId(productoId);
            nuevoItem.setCantidad(cantidad);
            nuevoItem.setPrecioUnitario(cantidad);
            carrito.getItems().add(nuevoItem);
        }

        double nuevoTotal = 0;
        for (ItemsCarrito item : carrito.getItems()) {
            nuevoTotal = nuevoTotal + item.getPrecioUnitario();
        }
        carrito.setTotalTemporal(nuevoTotal);
        return carritoRepository.save(null);
    }

    public Pedido realizarPedido(Long carritoId){
        return
    }

    public EstadoPedido consultarEstado(Long pedidoId){
        return
    }

    public List<Pedido> obtenerHistorialCompras(Long clienteId){
        return
    }

    public Pedido registrarVenta(Long pedidoId, String metodoPago){
        return
    }

    public boolean aplicarCupon(){
        return
    }


}
