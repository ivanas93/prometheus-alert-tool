package com.ivanas.metricgenerator.controller;

import com.ivanas.metricgenerator.service.MetricsGeneratorService;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

@RestController
@RequiredArgsConstructor
public class MetricsGeneratorController {
    private final MetricsGeneratorService metricsGeneratorService;

    private final MeterRegistry meterRegistry;

    private AtomicInteger gauge;

    @PostConstruct
    public void gaugeRegister() {
        this.gauge = meterRegistry.gauge("custom.metric", new AtomicInteger(0));
    }

    @GetMapping(value = "/metricsGenerator")
    public String getMetricsGenerated(@RequestParam final Map<String, String> params) {
        return Function.<Map<String, String>>identity()
                .andThen(this::carryMetricToGauge)
                .apply(params);
    }

    private String carryMetricToGauge(final Map<String, String> params) {
        var value = this.metricsGeneratorService.getMetricsGenerated(params.get("command")).getValueMetric();
        gauge.set(value);
        return "Value of gauge = " + value.toString();
    }
}
