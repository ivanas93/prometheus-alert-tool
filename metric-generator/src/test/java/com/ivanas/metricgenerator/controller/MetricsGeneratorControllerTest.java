package com.ivanas.metricgenerator.controller;

import com.ivanas.metricgenerator.model.HighMetric;
import com.ivanas.metricgenerator.service.MetricsGeneratorService;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        SimpleMeterRegistry.class,
        MetricsGeneratorController.class,
        MetricsGeneratorService.class
})
@WebMvcTest(controllers = MetricsGeneratorController.class)
class MetricsGeneratorControllerTest {

    @MockBean
    MetricsGeneratorService metricsGeneratorService;

    @Autowired
    MockMvc mockMvc;

    @SneakyThrows
    @Test
    void shouldReturnMetrics() {
        //Given
        when(metricsGeneratorService.getMetricsGenerated("INC")).thenReturn(new HighMetric(1));

        //when && Then
        mockMvc.perform(MockMvcRequestBuilders.get("/metricsGenerator?command=INC"))
                .andExpect(status().isOk())
                .andExpect(
                        content().json("{\"valueMetric\":1}")
                );

    }

}


