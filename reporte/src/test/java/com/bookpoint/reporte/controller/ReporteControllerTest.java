package com.bookpoint.reporte.controller;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bookpoint.reporte.dto.ReporteAutorDTO;
import com.bookpoint.reporte.dto.ReporteCategoriaDTO;
import com.bookpoint.reporte.dto.ReporteGeneralDTO;
import com.bookpoint.reporte.dto.ReporteSucursalDTO;
import com.bookpoint.reporte.service.ReporteService;

@WebMvcTest(ReporteController.class)
public class ReporteControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ReporteService service;

    @Test
    void categoria_deberiaRetornar200()
            throws Exception {

        when(service.reporteCategoria())
                .thenReturn(List.of(new ReporteCategoriaDTO()));

        mockMvc.perform(
                get("/api/v1/reportes/categoria")
        )
        .andExpect(
                status().isOk()
        );
    }

    @Test
    void autor_deberiaRetornar200()
            throws Exception {

        when(service.reporteAutor())
                .thenReturn(List.of(new ReporteAutorDTO()));

        mockMvc.perform(
                get("/api/v1/reportes/autor")
        )
        .andExpect(
                status().isOk()
        );
    }

    @Test
    void sucursal_deberiaRetornar200()
            throws Exception {

        when(service.reporteSucursal())
                .thenReturn(List.of(new ReporteSucursalDTO()));

        mockMvc.perform(
                get("/api/v1/reportes/sucursal")
        )
        .andExpect(
                status().isOk()
        );
    }

    @Test
    void todo_deberiaRetornar200()
            throws Exception {

        when(service.reporteGeneral())
                .thenReturn(new ReporteGeneralDTO());

        mockMvc.perform(
                get("/api/v1/reportes/todo")
        )
        .andExpect(
                status().isOk()
        );
    }
}

