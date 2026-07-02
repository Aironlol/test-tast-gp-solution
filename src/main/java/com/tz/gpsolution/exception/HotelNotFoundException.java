package com.tz.gpsolution.exception;

public class HotelNotFoundException extends RuntimeException {
    public HotelNotFoundException(Long id) {
        super("Отель не найден по id: " + id);
    }
}