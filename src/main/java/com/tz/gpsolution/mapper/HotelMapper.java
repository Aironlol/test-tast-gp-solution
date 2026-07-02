package com.tz.gpsolution.mapper;

import com.tz.gpsolution.model.dto.*;
import com.tz.gpsolution.model.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Mapper(componentModel = "spring")
public interface HotelMapper {

    @Mapping(target = "address", expression = "java(mapAddressToString(hotel.getAddress()))")
    @Mapping(target = "phone", source = "contacts.phone")
    HotelShortDto toShortDto(Hotel hotel);

    HotelFullDto toFullDto(Hotel hotel);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "amenities", ignore = true)
    Hotel toEntity(HotelCreateDto dto);

    default String mapAddressToString(Address address) {
        if (address == null) {
            return null;
        }
        return Stream.of(
                        address.getHouseNumber() != null ? address.getHouseNumber() + " " + address.getStreet() : address.getStreet(),
                        address.getCity(),
                        address.getPostCode(),
                        address.getCountry()
                )
                .filter(s -> s != null && !s.isBlank())
                .collect(Collectors.joining(", "));
    }
}