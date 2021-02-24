package com.ivanas.metricgenerator.controller;

import com.ivanas.metricgenerator.service.MetricsGeneratorService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@AllArgsConstructor
public class MetricsGeneratorController {
    private final MetricsGeneratorService metricsGeneratorService;

    @GetMapping(value = "/metricsGenerator")
    private String getMetricsGenerated(@RequestParam final Map<String, String> params) {
        return metricsGeneratorService.getMetricsGenerated(params.get("command")).calculateMetric().toString();
    }
}
