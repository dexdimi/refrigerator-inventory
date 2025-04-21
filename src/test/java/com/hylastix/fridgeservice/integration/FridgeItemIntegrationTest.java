package com.hylastix.fridgeservice.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hylastix.fridgeservice.dto.FridgeItemDTO;
import com.hylastix.fridgeservice.model.Category;
import com.hylastix.fridgeservice.model.FridgeItem;
import com.hylastix.fridgeservice.model.Unit;
import com.hylastix.fridgeservice.repository.FridgeItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class FridgeItemIntegrationTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private FridgeItemRepository repository;

    private UUID testItemId;

    @BeforeEach
    void setUp() {
        FridgeItem item = FridgeItem.builder()
                .name("Milk")
                .category(Category.DAIRY)
                .quantity(1)
                .unit(Unit.LITRES)
                .timeStored(LocalDateTime.now())
                .bestBeforeDate(LocalDate.now().plusDays(5))
                .build();
        item = repository.save(item);
        testItemId = item.getId();
    }

    @Test
    void shouldGetItemById() throws Exception {
        mockMvc.perform(get("/api/fridge-items/" + testItemId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Milk"))
                .andExpect(jsonPath("$.category").value("DAIRY"));
    }

    @Test
    void shouldCreateItem() throws Exception {
        FridgeItemDTO dto = FridgeItemDTO.builder()
                .name("Eggs")
                .category(com.hylastix.fridgeservice.model.Category.PROTEINS)
                .unit(Unit.PIECES)
                .quantity(12)
                .timeStored(LocalDateTime.now())
                .bestBeforeDate(LocalDate.now().plusDays(10))
                .build();

        mockMvc.perform(post("/api/fridge-items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Eggs"))
                .andExpect(jsonPath("$.category").value("PROTEINS"))
                .andExpect(jsonPath("$.unit").value("PIECES"));
    }

    @Test
    void shouldGetAllItems() throws Exception {
        mockMvc.perform(get("/api/fridge-items"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[0].name").value("Milk"))
                .andExpect(jsonPath("$.content[0].category").value("DAIRY"))
                .andExpect(jsonPath("$.content[0].unit").value("LITRES"));
    }

    @Test
    void shouldUpdateItem() throws Exception {
        FridgeItemDTO updateDto = FridgeItemDTO.builder()
                .id(testItemId)
                .name("Updated Milk")
                .category(Category.DAIRY)
                .quantity(3)
                .timeStored(LocalDateTime.now())
                .bestBeforeDate(LocalDate.now().plusDays(7))
                .build();

        mockMvc.perform(put("/api/fridge-items/" + testItemId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Milk"))
                .andExpect(jsonPath("$.quantity").value(3));
    }

    @Test
    void shouldDeleteItem() throws Exception {
        mockMvc.perform(delete("/api/fridge-items/" + testItemId))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/fridge-items/" + testItemId))
                .andExpect(status().isNotFound());
    }
}
