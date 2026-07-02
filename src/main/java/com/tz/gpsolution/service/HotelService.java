package com.tz.gpsolution.service;

import com.tz.gpsolution.exception.HotelNotFoundException;
import com.tz.gpsolution.mapper.HotelMapper;
import com.tz.gpsolution.model.dto.HotelCreateDto;
import com.tz.gpsolution.model.dto.HotelFullDto;
import com.tz.gpsolution.model.dto.HotelShortDto;
import com.tz.gpsolution.model.entity.Hotel;
import com.tz.gpsolution.repository.HotelRepository;
import com.tz.gpsolution.repository.HotelSpecification;
import com.tz.gpsolution.service.strategy.HistogramStrategy;
import com.tz.gpsolution.service.strategy.HistogramStrategyFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HotelService {

    private final HotelRepository hotelRepository;
    private final HotelMapper hotelMapper;
    private final HistogramStrategyFactory histogramStrategyFactory;

    @Transactional(readOnly = true)
    public List<HotelShortDto> getAllHotels() {
        return hotelRepository.findAll().stream()
                .map(hotelMapper::toShortDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public HotelFullDto getHotelById(Long id) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new HotelNotFoundException(id));
        return hotelMapper.toFullDto(hotel);
    }

    @Transactional(readOnly = true)
    public List<HotelShortDto> searchHotels(String name, String brand, String city, String country, String amenity) {
        Specification<Hotel> spec = Specification.where(HotelSpecification.hasName(name))
                .and(HotelSpecification.hasBrand(brand))
                .and(HotelSpecification.hasCity(city))
                .and(HotelSpecification.hasCountry(country))
                .and(HotelSpecification.hasAmenity(amenity));

        return hotelRepository.findAll(spec).stream()
                .map(hotelMapper::toShortDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public HotelShortDto createHotel(HotelCreateDto dto) {
        Hotel hotel = hotelMapper.toEntity(dto);
        Hotel savedHotel = hotelRepository.save(hotel);
        return hotelMapper.toShortDto(savedHotel);
    }

    @Transactional
    public void addAmenities(Long id, Collection<String> amenities) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new HotelNotFoundException(id));
        
        hotel.getAmenities().addAll(amenities);
        hotelRepository.save(hotel);
    }

    @Transactional(readOnly = true)
    public Map<String, Long> getHistogram(String param) {
        HistogramStrategy strategy = histogramStrategyFactory.getStrategy(param);
        return strategy.getHistogram();
    }
}