package com.tz.gpsolution.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ArrivalTimeDto {
    @NotBlank(message = "Время заезда обязательно")
    @Pattern(regexp = "^([01]?[0-9]|2[0-3]):[0-5][0-9]$", message = "Формат времени должен быть HH:mm")
    private String checkIn;

    @Pattern(regexp = "^([01]?[0-9]|2[0-3]):[0-5][0-9]$", message = "Формат времени должен быть HH:mm")
    private String checkOut;
}