package com.tz.gpsolution.service.strategy;

import com.tz.gpsolution.model.entity.Hotel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class AbstractHistogramStrategy implements HistogramStrategy {

    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public Map<String, Long> getHistogram() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> query = cb.createQuery(Object[].class);
        Root<Hotel> root = query.from(Hotel.class);

        Path<String> groupByPath = getGroupByPath(root, cb);

        query.multiselect(groupByPath, cb.count(root));
        query.groupBy(groupByPath);

        List<Object[]> results = entityManager.createQuery(query).getResultList();

        return results.stream()
                .filter(result -> result[0] != null)
                .collect(Collectors.toMap(
                        result -> (String) result[0],
                        result -> (Long) result[1]
                ));
    }

    protected abstract Path<String> getGroupByPath(Root<Hotel> root, CriteriaBuilder cb);
}