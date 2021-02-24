package com.ivanas.metricgenerator.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.ivanas.metricgenerator.model.BaseMetric;
import com.ivanas.metricgenerator.model.HighMetric;
import com.ivanas.metricgenerator.model.LowMetric;
import com.ivanas.metricgenerator.model.ZeroMetric;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.concurrent.TimeUnit;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;

@Service
public class MetricsGeneratorServiceImpl implements MetricsGeneratorService {
    private final LoadingCache<Integer, Integer> cache;

    public MetricsGeneratorServiceImpl() {
        this.cache = this.createCache();
    }

    private LoadingCache<Integer, Integer> createCache() {
        var cacheLoader = new CacheLoader<Integer, Integer>() {
            @Override
            public Integer load(final @Nullable Integer integer) throws Exception {
                return 0;
            }
        };
        return CacheBuilder.newBuilder()
                .maximumSize(1)
                .expireAfterAccess(2L, TimeUnit.MINUTES)
                .build(cacheLoader);
    }


    @Override
    public BaseMetric getMetricsGenerated(final String command) {
        var metric = Match(command).of(
                Case($("RESET"), new ZeroMetric(cache.getUnchecked(0))),
                Case($("INC"), new HighMetric(cache.getUnchecked(0))),
                Case($("DEC"), new LowMetric(cache.getUnchecked(0))),
                Case($(), new ZeroMetric(cache.getUnchecked(0))));
        cache.put(0, metric.calculateMetric());
        return metric;
    }
}
