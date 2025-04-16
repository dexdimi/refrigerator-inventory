package com.hylastix.fridgeservice.dto;

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
    private String unit;
    private LocalDate bestBeforeDate;
    private LocalDateTime timeStored;
    private String category;
    private String notes;
}
