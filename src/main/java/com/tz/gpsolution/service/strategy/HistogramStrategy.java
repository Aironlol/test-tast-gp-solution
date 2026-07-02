package com.tz.gpsolution.service.strategy;

import java.util.Map;

public interface HistogramStrategy {
    String BRAND = "brand";
    String CITY = "city";
    String COUNTRY = "country";
    String AMENITIES = "amenities";
    String ADDRESS = "address";

    Map<String, Long> getHistogram();
    String getParamName();
}