package com.tz.gpsolution.model.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.Valid;

@Data
public class HotelCreateDto {
    @NotBlank(message = "Название отеля не может быть пустым")
    private String name;
    
    private String description;
    
    @NotBlank(message = "Название бренда не может быть пустым")
    private String brand;
    
    @NotNull(message = "Адрес обязателен для заполнения")
    @Valid
    private AddressDto address;
    
    @NotNull(message = "Контакты обязательны для заполнения")
    @Valid
    private ContactsDto contacts;
    
    @NotNull(message = "Время заезда/выезда обязательно для заполнения")
    @Valid
    private ArrivalTimeDto arrivalTime;
}