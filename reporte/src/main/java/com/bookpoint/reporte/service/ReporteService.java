package com.bookpoint.reporte.service;

import java.util.ArrayList;
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

        List<Map<String, Object>> ventas = new ArrayList<>();

        try {

            List<Map<String, Object>> presencial =
                    restTemplate.getForObject(
                            VENTAS_PRESENCIAL_URL,
                            List.class
                    );

            if (presencial != null) {
                ventas.addAll(presencial);
            }

        } catch (Exception ignored) {
        }

        try {

            List<Map<String, Object>> online =
                    restTemplate.getForObject(
                            VENTAS_ONLINE_URL,
                            List.class
                    );

            if (online != null) {
                ventas.addAll(online);
            }

        } catch (Exception ignored) {
        }

        return ventas;
    }

    @SuppressWarnings("unchecked")
    private List<Map<String, Object>> obtenerDetalles(
            List<Map<String, Object>> ventas) {

        return ventas.stream()
                .flatMap(v ->
                        ((List<Map<String, Object>>) v.get("detalleProductos"))
                                .stream())
                .toList();
    }

    private int calcularIngresos(
            List<Map<String, Object>> detalles) {

        return detalles.stream()
                .mapToInt(d ->

                        Integer.parseInt(
                                d.get("precio").toString())

                                *

                                Integer.parseInt(
                                        d.get("cantidad").toString())

                )
                .sum();
    }


    // CATEGORÍA


    public List<ReporteCategoriaDTO> reporteCategoria() {

        List<Map<String, Object>> detalles =
                obtenerDetalles(obtenerVentas());

        return detalles.stream()

                .filter(d -> d.get("categoria") != null)

                .collect(Collectors.groupingBy(
                        d -> d.get("categoria").toString(),
                        Collectors.toList()
                ))

                .entrySet()

                .stream()

                .map(e -> {

                    ReporteCategoriaDTO dto =
                            new ReporteCategoriaDTO();

                    dto.setCategoria(e.getKey());

                    dto.setTotalVentas(
                            e.getValue().size());

                    dto.setTotalIngresos(
                            calcularIngresos(e.getValue()));

                    return dto;

                })

                .toList();
    }


    // AUTOR


    public List<ReporteAutorDTO> reporteAutor() {

        List<Map<String, Object>> detalles =
                obtenerDetalles(obtenerVentas());

        return detalles.stream()

                .filter(d -> d.get("autor") != null)

                .collect(Collectors.groupingBy(
                        d -> d.get("autor").toString(),
                        Collectors.toList()
                ))

                .entrySet()

                .stream()

                .map(e -> {

                    ReporteAutorDTO dto =
                            new ReporteAutorDTO();

                    dto.setAutor(e.getKey());

                    dto.setTotalVentas(
                            e.getValue().size());

                    dto.setTotalIngresos(
                            calcularIngresos(e.getValue()));

                    return dto;

                })

                .toList();
    }


    // SUCURSAL


    public List<ReporteSucursalDTO> reporteSucursal() {

        List<Map<String, Object>> detalles =
                obtenerDetalles(obtenerVentas());

        return detalles.stream()

                .collect(Collectors.groupingBy(
                        d -> d.get("sucursalId").toString(),
                        Collectors.toList()
                ))

                .entrySet()

                .stream()

                .map(e -> {

                    ReporteSucursalDTO dto =
                            new ReporteSucursalDTO();

                    dto.setSucursal(e.getKey());

                    dto.setTotalVentas(
                            e.getValue().size());

                    dto.setTotalIngresos(
                            calcularIngresos(e.getValue()));

                    return dto;

                })

                .toList();
    }


    // GENERAL


    public ReporteGeneralDTO reporteGeneral() {

        ReporteGeneralDTO general =
                new ReporteGeneralDTO();

        general.setPorCategoria(
                reporteCategoria());

        general.setPorAutor(
                reporteAutor());

        general.setPorSucursal(
                reporteSucursal());

        return general;
    }

}