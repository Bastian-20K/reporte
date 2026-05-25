package com.bookpoint.reporte.dto;

import lombok.Data;

@Data
public class ReporteSucursalDTO {
    private String sucursal;

    private Integer totalVentas;

    private Integer totalIngresos;
}
