package com.bookpoint.reporte.dto;

import java.util.List;

import lombok.Data;

@Data
public class ReporteGeneralDTO {
    private List<ReporteCategoriaDTO> porCategoria;

    private List<ReporteAutorDTO> porAutor;

    private List<ReporteSucursalDTO> porSucursal;
}
