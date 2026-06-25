package com.bookpoint.reporte.controller;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(
    properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect"
    }
)
@AutoConfigureMockMvc
public class ReporteControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RestTemplate restTemplate;

    @BeforeEach
    void setup() {

        when(
                restTemplate.getForObject(
                        anyString(),
                        any(Class.class)
                )
        ).thenReturn(List.of());
    }

    @Test
    void categoria_deberiaRetornar200()
            throws Exception {

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

        mockMvc.perform(
                get("/api/v1/reportes/todo")
        )
        .andExpect(
                status().isOk()
        );
    }
}

