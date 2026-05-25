package com.bookpoint.reporte.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookpoint.reporte.dto.ReporteAutorDTO;
import com.bookpoint.reporte.dto.ReporteCategoriaDTO;
import com.bookpoint.reporte.dto.ReporteGeneralDTO;
import com.bookpoint.reporte.dto.ReporteSucursalDTO;
import com.bookpoint.reporte.service.ReporteService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/reportes")
@RequiredArgsConstructor
public class ReporteController {
    private final ReporteService service;

    @GetMapping("/categoria")
    public List<ReporteCategoriaDTO> categoria() {
        return service.reporteCategoria();
    }

    @GetMapping("/autor")
    public List<ReporteAutorDTO> autor() {
        return service.reporteAutor();
    }

    @GetMapping("/sucursal")
    public List<ReporteSucursalDTO> sucursal() {
        return service.reporteSucursal();
    }

    @GetMapping("/todo")
    public ReporteGeneralDTO todo() {
        return service.reporteGeneral();
    }
}
