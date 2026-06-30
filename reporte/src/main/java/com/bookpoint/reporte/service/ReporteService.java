package com.bookpoint.reporte.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.bookpoint.reporte.dto.ReporteAutorDTO;
import com.bookpoint.reporte.dto.ReporteCategoriaDTO;
import com.bookpoint.reporte.dto.ReporteGeneralDTO;
import com.bookpoint.reporte.dto.ReporteSucursalDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReporteService {
    private final RestTemplate restTemplate;

    private static final String VENTAS_URL = "http://localhost:8085/api/v1/ventas";

    public List<Map<String, Object>> obtenerVentas() {

    List<Map<String, Object>> ventas =
                restTemplate.getForObject(
                        VENTAS_URL,
                        List.class
                );

        return ventas == null
                ? List.of()
                : ventas;
        }

    public List<ReporteCategoriaDTO> reporteCategoria() {

        List<Map<String, Object>> ventas = obtenerVentas();
        return ventas.stream()
                .collect(Collectors.groupingBy(
                        v -> v.get("categoria").toString(),
                        Collectors.toList()
                ))
                .entrySet()
                .stream()
                .map(e -> {
                    ReporteCategoriaDTO dto = new ReporteCategoriaDTO();
                    dto.setCategoria(e.getKey());
                    dto.setTotalVentas(e.getValue().size());
                    dto.setTotalIngresos(
                            e.getValue().stream()
                                    .mapToInt(v ->
                                            Integer.parseInt(v.get("totalPagar").toString())
                                    ).sum()
                    );
                    return dto;
                })
                .toList();
    }

    public List<ReporteAutorDTO> reporteAutor() {

        List<Map<String, Object>> ventas = obtenerVentas();
        return ventas.stream()
                .collect(Collectors.groupingBy(
                        v -> v.get("autor").toString(),
                        Collectors.toList()
                ))
                .entrySet()
                .stream()
                .map(e -> {
                    ReporteAutorDTO dto = new ReporteAutorDTO();
                    dto.setAutor(e.getKey());
                    dto.setTotalVentas(e.getValue().size());
                    dto.setTotalIngresos(
                            e.getValue().stream()
                                    .mapToInt(v ->
                                            Integer.parseInt(v.get("totalPagar").toString())
                                    ).sum()
                    );
                    return dto;
                })
                .toList();
    }

    public List<ReporteSucursalDTO> reporteSucursal() {

        List<Map<String, Object>> ventas = obtenerVentas();
        return ventas.stream()
                .collect(Collectors.groupingBy(
                        v -> v.get("sucursal").toString(),
                        Collectors.toList()
                ))
                .entrySet()
                .stream()
                .map(e -> {
                    ReporteSucursalDTO dto = new ReporteSucursalDTO();
                    dto.setSucursal(e.getKey());
                    dto.setTotalVentas(e.getValue().size());
                    dto.setTotalIngresos(
                            e.getValue().stream()
                                    .mapToInt(v ->
                                            Integer.parseInt(v.get("totalPagar").toString())
                                    ).sum()
                    );
                    return dto;
                })
                .toList();
    }

    public ReporteGeneralDTO reporteGeneral() {
        ReporteGeneralDTO general = new ReporteGeneralDTO();
        general.setPorCategoria(reporteCategoria());
        general.setPorAutor(reporteAutor());
        general.setPorSucursal(reporteSucursal());
        return general;
    }
}
