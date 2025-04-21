package com.hylastix.fridgeservice.service;

import com.hylastix.fridgeservice.dto.FridgeItemDTO;
import com.hylastix.fridgeservice.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.UUID;

public interface FridgeItemService {

    FridgeItemDTO createFridgeItem(FridgeItemDTO itemDTO);

    FridgeItemDTO getFridgeItemById(UUID id);

    Page<FridgeItemDTO> getFridgeItems(String name, Category category, Pageable pageable);

    FridgeItemDTO updateFridgeItem(UUID id, FridgeItemDTO itemDTO);

    void deleteFridgeItem(UUID id);
}
