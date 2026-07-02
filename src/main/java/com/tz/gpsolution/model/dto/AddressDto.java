package com.tz.gpsolution.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddressDto {
    @NotNull(message = "Номер дома обязателен")
    private Integer houseNumber;

    @NotBlank(message = "Улица обязательна")
    private String street;

    @NotBlank(message = "Город обязателен")
    private String city;

    @NotBlank(message = "Страна обязательна")
    private String country;

    @NotBlank(message = "Почтовый индекс обязателен")
    private String postCode;
}