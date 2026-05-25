package com.bookpoint.reporte.dto;

import lombok.Data;

@Data
public class ReporteCategoriaDTO {
    private String categoria;

    private Integer totalVentas;

    private Integer totalIngresos;
}
