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

    private static final String VENTAS_PRESENCIAL_URL =
            "http://localhost:8085/api/v1/ventas/presencial";

    private static final String VENTAS_ONLINE_URL =
            "http://localhost:8085/api/v1/ventas/online";

    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> obtenerVentas() {

        List<Map<String, Object>> ventas =
                new java.util.ArrayList<>();

        try {
            List<Map<String, Object>> ventasPresenciales =
                    restTemplate.getForObject(
                            VENTAS_PRESENCIAL_URL,
                            List.class
                    );

            if (ventasPresenciales != null) {
                ventas.addAll(ventasPresenciales);
            }

        } catch (Exception ignored) {}

        try {
            List<Map<String, Object>> ventasOnline =
                    restTemplate.getForObject(
                            VENTAS_ONLINE_URL,
                            List.class
                    );

            if (ventasOnline != null) {
                ventas.addAll(ventasOnline);
            }

        } catch (Exception ignored) {}

        return ventas;
    }

    // 🔥 MÉTODO BASE PARA EVITAR REPETICIÓN
    @SuppressWarnings("unchecked")
    private List<Map<String, Object>> obtenerDetalles(List<Map<String, Object>> ventas) {

        return ventas.stream()
                .flatMap(v ->
                        ((List<Map<String, Object>>) v.get("detalleProductos")).stream()
                )
                .toList();
    }

    // =========================
    // CATEGORÍA
    // =========================
    public List<ReporteCategoriaDTO> reporteCategoria() {

        List<Map<String, Object>> ventas = obtenerVentas();

        List<Map<String, Object>> detalles = obtenerDetalles(ventas);

        return detalles.stream()
                .collect(Collectors.groupingBy(
                        d -> d.get("categoria").toString(),
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
                                            Integer.parseInt(v.get("precio").toString())
                                    ).sum()
                    );
                    return dto;
                })
                .toList();
    }

    // =========================
    // AUTOR
    // =========================
    public List<ReporteAutorDTO> reporteAutor() {

        List<Map<String, Object>> ventas = obtenerVentas();

        List<Map<String, Object>> detalles = obtenerDetalles(ventas);

        return detalles.stream()
                .collect(Collectors.groupingBy(
                        d -> d.get("autor").toString(),
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
                                            Integer.parseInt(v.get("precio").toString())
                                    ).sum()
                    );
                    return dto;
                })
                .toList();
    }

    // =========================
    // SUCURSAL (AQUÍ SÍ HAY ONLINE + PRESENCIAL)
    // =========================
    public List<ReporteSucursalDTO> reporteSucursal() {

        List<Map<String, Object>> ventas = obtenerVentas();

        List<Map<String, Object>> detalles = obtenerDetalles(ventas);

        return detalles.stream()
                .collect(Collectors.groupingBy(
                        d -> d.get("sucursalId").toString(),
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
                                            Integer.parseInt(v.get("precio").toString())
                                    ).sum()
                    );
                    return dto;
                })
                .toList();
    }

    // =========================
    // GENERAL
    // =========================
    public ReporteGeneralDTO reporteGeneral() {
        ReporteGeneralDTO general = new ReporteGeneralDTO();
        general.setPorCategoria(reporteCategoria());
        general.setPorAutor(reporteAutor());
        general.setPorSucursal(reporteSucursal());
        return general;
    }
}