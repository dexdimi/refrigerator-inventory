package com.hylastix.fridgeservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hylastix.fridgeservice.model.Category;
import com.hylastix.fridgeservice.model.Unit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FridgeItemDTO {

    private UUID id;

    private String name;

    private int quantity;

    private Unit unit;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate bestBeforeDate;

    private LocalDateTime timeStored;

    private Category category;

    private String notes;
}
