package com.tz.gpsolution.repository;

import com.tz.gpsolution.model.entity.Hotel;
import org.springframework.data.jpa.domain.Specification;

public class HotelSpecification {

    private static final String NAME = "name";
    private static final String BRAND = "brand";
    private static final String ADDRESS = "address";
    private static final String CITY = "city";
    private static final String COUNTRY = "country";
    private static final String AMENITIES = "amenities";

    public static Specification<Hotel> hasName(String name) {
        return (root, query, cb) -> name == null ? null : cb.equal(cb.lower(root.get(NAME)), name.toLowerCase());
    }

    public static Specification<Hotel> hasBrand(String brand) {
        return (root, query, cb) -> brand == null ? null : cb.equal(cb.lower(root.get(BRAND)), brand.toLowerCase());
    }

    public static Specification<Hotel> hasCity(String city) {
        return (root, query, cb) -> city == null ? null : cb.equal(cb.lower(root.get(ADDRESS).get(CITY)), city.toLowerCase());
    }

    public static Specification<Hotel> hasCountry(String country) {
        return (root, query, cb) -> country == null ? null : cb.equal(cb.lower(root.get(ADDRESS).get(COUNTRY)), country.toLowerCase());
    }

    public static Specification<Hotel> hasAmenity(String amenity) {
        return (root, query, cb) -> {
            if (amenity == null) return null;
            return cb.equal(cb.lower(root.join(AMENITIES)), amenity.toLowerCase());
        };
    }
}