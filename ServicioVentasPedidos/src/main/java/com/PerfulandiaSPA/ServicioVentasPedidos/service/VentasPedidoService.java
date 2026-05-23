package com.PerfulandiaSPA.ServicioVentasPedidos.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.PerfulandiaSPA.ServicioVentasPedidos.dto.FacturaDTO;
import com.PerfulandiaSPA.ServicioVentasPedidos.dto.ProductoDTO;
import com.PerfulandiaSPA.ServicioVentasPedidos.dto.StockDTO;
import com.PerfulandiaSPA.ServicioVentasPedidos.model.Carrito;
import com.PerfulandiaSPA.ServicioVentasPedidos.model.EstadoPedido;
import com.PerfulandiaSPA.ServicioVentasPedidos.model.ItemsCarrito;
import com.PerfulandiaSPA.ServicioVentasPedidos.model.MetodoPago;
import com.PerfulandiaSPA.ServicioVentasPedidos.model.Pedido;
import com.PerfulandiaSPA.ServicioVentasPedidos.model.TipoEnvio;
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
            nuevoCarrito.setItems(new ArrayList<>());
            carrito = carritoRepository.save(nuevoCarrito);
        }

        boolean productoExiste = false;
        for (ItemsCarrito item : carrito.getItems()) {
            if (item.getProductoId().equals(productoId)){
                item.setCantidad(item.getCantidad() + cantidad);
                productoExiste = true;
                break;
            }
        }

        if (!productoExiste){
            ItemsCarrito nuevoItem = new ItemsCarrito();
            nuevoItem.setProductoId(producto.getId());
            nuevoItem.setCantidad(cantidad);
            nuevoItem.setPrecioUnitario(producto.getPrecio());
            carrito.getItems().add(nuevoItem);
        }

        double nuevoTotal = 0;
        for (ItemsCarrito item : carrito.getItems()) {
            nuevoTotal = nuevoTotal + item.getPrecioUnitario() * item.getCantidad();
        }
        carrito.setTotalTemporal(nuevoTotal);
        return carritoRepository.save(carrito);
    }

    public Pedido realizarPedido(Long carritoId, String cupon, MetodoPago metodoPago, TipoEnvio tipoEnvio, String direccion){
        Carrito carrito = carritoRepository.findById(carritoId).orElse(null);
        if (carrito == null) {
            return null;
        }

        String urlFactura ="http://localhost:8082/api/v1/facturas/"; //TODO conectar correctamente con factura
        FacturaDTO factura = restTemplate.postForObject(urlFactura, FacturaDTO.class, null);

        LocalDateTime fecha = LocalDateTime.now();
        Pedido pedido = new Pedido();
        pedido.setClienteId(carrito.getClienteId());
        pedido.setFacturaId(factura.getId());
        pedido.setFecha(fecha);
        pedido.setTotal(carrito.getTotalTemporal());
        pedido.setEstado(EstadoPedido.PENDIENTE);
        pedido.setMetodoPago(metodoPago);
        pedido.setCuponAplicado(cupon);
        pedido.setTipoEnvio(tipoEnvio);
        if (tipoEnvio.equals(TipoEnvio.ENVIO_RAPIDO)) {
            pedido.setCostoEnvio(5000);
            pedido.setTotal(carrito.getTotalTemporal() + pedido.getCostoEnvio());
        }
        if (tipoEnvio.equals(TipoEnvio.ENVIO_ESTANDAR)) {
            pedido.setCostoEnvio(0);
        }
        pedido.setDireccion(direccion);
        pedidoRepository.save(pedido);

        carrito.getItems().clear();
        carrito.setTotalTemporal(0);
        carritoRepository.save(carrito);

        return pedido;
    }

    public EstadoPedido consultarEstado(Long pedidoId){
        Pedido pedido = pedidoRepository.findById(pedidoId).orElse(null);
        if (pedido != null) {
            return pedido.getEstado();
        }
        return null;
    }

    public List<Pedido> obtenerHistorialCompras(Long clienteId){
        List<Pedido> listaPedidos = pedidoRepository.findByClienteId(clienteId);
        return listaPedidos;
    }

    public Pedido registrarVenta(Long pedidoId){
        Pedido pedido = pedidoRepository.findById(pedidoId).orElse(null);
        if (pedido != null){
            pedido.setEstado(EstadoPedido.APROBADO);
            aplicarCupon(pedido);
            pedidoRepository.save(pedido);
        }
        return null;
    }

    public void aplicarCupon(Pedido pedido){
        if (pedido.getCuponAplicado() != null){
            double descuento = pedido.getTotal() * 0.8;
            pedido.setTotal(descuento);
            pedidoRepository.save(pedido);
        }
    }

}
