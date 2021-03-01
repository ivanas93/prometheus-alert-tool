package com.ivanas.metricgenerator.controller;

import com.ivanas.metricgenerator.model.BaseMetric;
import com.ivanas.metricgenerator.service.MetricsGeneratorService;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import javax.annotation.PostConstruct;

@RestController
@RequiredArgsConstructor
public class MetricsGeneratorController {
    private final MetricsGeneratorService metricsGeneratorService;

    private final MeterRegistry meterRegistry;

    private AtomicInteger gauge;

    @PostConstruct
    public void gaugeRegister() {
        this.gauge = meterRegistry.gauge("custom_metric",
                Tags.of("creation_instant", Long.toString(System.nanoTime())), new AtomicInteger(0));
    }

    @GetMapping(value = "/inc")
    public BaseMetric increaseMetric() {
        return getMapBaseMetricFunction().apply(Map.of("command", "INC"));
    }

    @GetMapping(value = "/dec")
    public BaseMetric decreaseMetric() {
        return getMapBaseMetricFunction().apply(Map.of("command", "DEC"));
    }

    @GetMapping(value = "/reset")
    public BaseMetric resetMetric() {
        return getMapBaseMetricFunction().apply(Map.of("command", "RESET"));
    }

    private Function<Map<String, String>, BaseMetric> getMapBaseMetricFunction() {
        return Function.<Map<String, String>>identity().andThen(this::carryMetricToGauge);
    }

    private BaseMetric carryMetricToGauge(final Map<String, String> params) {
        var metric = this.metricsGeneratorService.getMetricsGenerated(params.get("command"));
        gauge.set(metric.getValueMetric());
        return metric;
    }
}
