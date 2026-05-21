package com.PerfulandiaSPA.ServicioVentasPedidos.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemsCarrito {
    @NotNull
    private Long productoId;

    @Min(value = 1, message = "Debe ingresar una cantidad mayor a cero")
    private int cantidad;

    @Min(value = 0, message = "Debe ingresar un precio igual o mayor a cero")
    private double precioUnitario;
}