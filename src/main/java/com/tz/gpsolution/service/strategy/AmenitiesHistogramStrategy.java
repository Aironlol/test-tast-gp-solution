package com.tz.gpsolution.service.strategy;

import com.tz.gpsolution.model.entity.Hotel;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Component;

@Component
public class AmenitiesHistogramStrategy extends AbstractHistogramStrategy {

    @Override
    protected Path<String> getGroupByPath(Root<Hotel> root, CriteriaBuilder cb) {
        return root.join(AMENITIES);
    }

    @Override
    public String getParamName() {
        return AMENITIES;
    }
}