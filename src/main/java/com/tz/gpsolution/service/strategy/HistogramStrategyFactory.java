package com.tz.gpsolution.service.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class HistogramStrategyFactory {

    private final Map<String, HistogramStrategy> strategies;

    @Autowired
    public HistogramStrategyFactory(List<HistogramStrategy> strategyList) {
        this.strategies = strategyList.stream()
                .collect(Collectors.toMap(
                        strategy -> strategy.getParamName().toLowerCase(),
                        Function.identity()
                ));
    }

    public HistogramStrategy getStrategy(String paramName) {
        HistogramStrategy strategy = strategies.get(paramName.toLowerCase());
        if (strategy == null) {
            throw new IllegalArgumentException("Неверный параметр гистограммы: " + paramName);
        }
        return strategy;
    }
}