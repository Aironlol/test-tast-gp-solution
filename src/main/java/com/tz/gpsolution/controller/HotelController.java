package com.tz.gpsolution.controller;

import com.tz.gpsolution.model.dto.HotelCreateDto;
import com.tz.gpsolution.model.dto.HotelFullDto;
import com.tz.gpsolution.model.dto.HotelShortDto;
import com.tz.gpsolution.service.HotelService;
import com.tz.gpsolution.validation.ValidAmenities;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@Validated
@Tag(name = "Отели", description = "API для управления отелями")
public class HotelController {

    private final HotelService hotelService;

    @GetMapping("/hotels")
    @Operation(summary = "Получить все отели", description = "Возвращает список всех отелей с их краткой информацией")
    public List<HotelShortDto> getAllHotels() {
        return hotelService.getAllHotels();
    }

    @GetMapping("/hotels/{id}")
    @Operation(summary = "Получить отель по ID", description = "Возвращает полную информацию по конкретному отелю")
    public HotelFullDto getHotelById(@PathVariable Long id) {
        return hotelService.getHotelById(id);
    }

    @GetMapping("/search")
    @Operation(summary = "Поиск отелей", description = "Поиск отелей по параметрам: name, brand, city, country, amenities")
    public List<HotelShortDto> searchHotels(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String amenities) {
        return hotelService.searchHotels(name, brand, city, country, amenities);
    }

    @PostMapping("/hotels")
    @Operation(summary = "Создать новый отель", description = "Создает новый отель и возвращает его краткую информацию")
    public ResponseEntity<HotelShortDto> createHotel(@Valid @RequestBody HotelCreateDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(hotelService.createHotel(dto));
    }

    @PostMapping("/hotels/{id}/amenities")
    @Operation(summary = "Добавить удобства отелю", description = "Добавляет список удобств (amenities) к существующему отелю")
    public ResponseEntity<Void> addAmenities(@PathVariable Long id, @RequestBody @ValidAmenities Set<String> amenities) {
        hotelService.addAmenities(id, amenities);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/histogram/{param}")
    @Operation(summary = "Получить гистограмму", description = "Возвращает количество отелей, сгруппированных по указанному параметру")
    public Map<String, Long> getHistogram(@PathVariable String param) {
        return hotelService.getHistogram(param);
    }
}