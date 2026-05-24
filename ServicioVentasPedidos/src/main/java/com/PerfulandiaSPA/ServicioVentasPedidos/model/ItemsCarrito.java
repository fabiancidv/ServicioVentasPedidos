package com.PerfulandiaSPA.ServicioVentasPedidos.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "items_carritos")
public class ItemsCarrito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long productoId;

    @Min(value = 1, message = "Debe ingresar una cantidad mayor a cero")
    private int cantidad;

    @Min(value = 0, message = "Debe ingresar un precio igual o mayor a cero")
    private double precioUnitario;
}