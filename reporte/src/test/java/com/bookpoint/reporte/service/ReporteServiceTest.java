package com.bookpoint.reporte.service;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import com.bookpoint.reporte.dto.ReporteAutorDTO;
import com.bookpoint.reporte.dto.ReporteCategoriaDTO;
import com.bookpoint.reporte.dto.ReporteGeneralDTO;
import com.bookpoint.reporte.dto.ReporteSucursalDTO;

@ExtendWith(MockitoExtension.class)
public class ReporteServiceTest {
    
    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ReporteService service;

    private List<Map<String,Object>> ventasMock() {

        return List.of(

                Map.of(
                        "categoria", "LIBROS",
                        "autor", "Gabriel Garcia Marquez",
                        "sucursal", "Santiago",
                        "totalPagar", 10000
                ),

                Map.of(
                        "categoria", "LIBROS",
                        "autor", "Gabriel Garcia Marquez",
                        "sucursal", "Santiago",
                        "totalPagar", 15000
                ),

                Map.of(
                        "categoria", "MANGA",
                        "autor", "Eiichiro Oda",
                        "sucursal", "Valparaiso",
                        "totalPagar", 20000
                )
        );
    }

    @Test
    void obtenerVentas_deberiaRetornarLista() {

        when(
                restTemplate.getForObject(
                        anyString(),
                        any(Class.class)
                )
        ).thenReturn(ventasMock());

        List<Map<String,Object>> resultado =
                service.obtenerVentas();

        assertEquals(
                3,
                resultado.size()
        );
    }

    @Test
    void reporteCategoria_deberiaAgruparCorrectamente() {

        when(
                restTemplate.getForObject(
                        anyString(),
                        any(Class.class)
                )
        ).thenReturn(ventasMock());

        List<ReporteCategoriaDTO> resultado =
                service.reporteCategoria();

        assertEquals(
                2,
                resultado.size()
        );
    }

    @Test
    void reporteAutor_deberiaAgruparCorrectamente() {

        when(
                restTemplate.getForObject(
                        anyString(),
                        any(Class.class)
                )
        ).thenReturn(ventasMock());

        List<ReporteAutorDTO> resultado =
                service.reporteAutor();

        assertEquals(
                2,
                resultado.size()
        );
    }

    @Test
    void reporteSucursal_deberiaAgruparCorrectamente() {

        when(
                restTemplate.getForObject(
                        anyString(),
                        any(Class.class)
                )
        ).thenReturn(ventasMock());

        List<ReporteSucursalDTO> resultado =
                service.reporteSucursal();

        assertEquals(
                2,
                resultado.size()
        );
    }

    @Test
    void reporteGeneral_deberiaRetornarDatos() {

        when(
                restTemplate.getForObject(
                        anyString(),
                        any(Class.class)
                )
        ).thenReturn(ventasMock());

        ReporteGeneralDTO resultado =
                service.reporteGeneral();

        assertNotNull(resultado);

        assertNotNull(
                resultado.getPorCategoria()
        );

        assertNotNull(
                resultado.getPorAutor()
        );

        assertNotNull(
                resultado.getPorSucursal()
        );
    }
}

