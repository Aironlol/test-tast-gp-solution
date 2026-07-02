package com.tz.gpsolution.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tz.gpsolution.model.dto.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class HotelControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private HotelCreateDto createDto;

    @BeforeEach
    void setUp() {
        createDto = new HotelCreateDto();
        createDto.setName("Тестовый отель");
        createDto.setDescription("Тестовое описание");
        createDto.setBrand("Тестовый бренд");
        
        AddressDto address = new AddressDto();
        address.setHouseNumber(1);
        address.setStreet("Тестовая улица");
        address.setCity("Минск");
        address.setCountry("Беларусь");
        address.setPostCode("12345");
        createDto.setAddress(address);

        ContactsDto contacts = new ContactsDto();
        contacts.setPhone("+123456789");
        contacts.setEmail("test@test.com");
        createDto.setContacts(contacts);

        ArrivalTimeDto arrivalTime = new ArrivalTimeDto();
        arrivalTime.setCheckIn("14:00");
        arrivalTime.setCheckOut("12:00");
        createDto.setArrivalTime(arrivalTime);
    }

    @Test
    @DisplayName("Должен успешно создать отель")
    void shouldCreateHotel() throws Exception {
        mockMvc.perform(post("/hotels")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("Тестовый отель")))
                .andExpect(jsonPath("$.phone", is("+123456789")))
                .andExpect(jsonPath("$.address", containsString("Тестовая улица")));
    }

    @Test
    @DisplayName("Должен вернуть гистограмму по городам")
    void shouldGetHistogram() throws Exception {
        mockMvc.perform(post("/hotels")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createDto)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/histogram/city"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.['Минск']", greaterThanOrEqualTo(1)));
    }

    @Test
    @DisplayName("Должен успешно добавить удобства к отелю")
    void shouldAddAmenities() throws Exception {
        String response = mockMvc.perform(post("/hotels")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createDto)))
                .andReturn().getResponse().getContentAsString();

        Long hotelId = objectMapper.readTree(response).get("id").asLong();

        Set<String> amenities = Set.of("WiFi", "Парковка");

        mockMvc.perform(post("/hotels/" + hotelId + "/amenities")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(amenities)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/hotels/" + hotelId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amenities", hasItems("WiFi", "Парковка")));
    }

    @Test
    @DisplayName("Должен вернуть ошибку при создании отеля с некорректными данными")
    void shouldFailWhenCreatingHotelWithInvalidData() throws Exception {
        HotelCreateDto invalidDto = new HotelCreateDto();

        mockMvc.perform(post("/hotels")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.name").exists())
                .andExpect(jsonPath("$.errors.brand").exists())
                .andExpect(jsonPath("$.errors.address").exists());
    }

    @Test
    @DisplayName("Должен вернуть ошибку при добавлении пустого списка удобств")
    void shouldFailWhenAddingEmptyAmenities() throws Exception {
        mockMvc.perform(post("/hotels/1/amenities")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Set.of())))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.['addAmenities.amenities']", is("Список удобств не может быть пустым")));
    }

    @Test
    @DisplayName("Должен вернуть ошибку при добавлении списка с пустыми удобствами")
    void shouldFailWhenAddingInvalidAmenities() throws Exception {
        mockMvc.perform(post("/hotels/1/amenities")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Set.of("WiFi", "  ", ""))))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.['addAmenities.amenities']", is("Удобство не может быть null или пустой строкой")));
    }

    @Test
    @DisplayName("Должен вернуть ошибку при запросе гистограммы с неверным параметром")
    void shouldFailWhenGettingHistogramWithInvalidParam() throws Exception {
        mockMvc.perform(get("/histogram/invalid_param"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is("Неверный параметр гистограммы: invalid_param")));
    }
}
