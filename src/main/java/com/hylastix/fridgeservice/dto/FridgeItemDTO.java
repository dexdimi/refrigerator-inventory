package com.hylastix.fridgeservice.dto;

import com.hylastix.fridgeservice.model.Category;
import com.hylastix.fridgeservice.model.Unit;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class FridgeItemDTO {

    private UUID id;
    private String name;
    private int quantity;
    private Unit unit;
    private LocalDate bestBeforeDate;
    private LocalDateTime timeStored;
    private Category category;
    private String notes;
}
