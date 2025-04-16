package com.hylastix.fridgeservice.service;

import com.hylastix.fridgeservice.dto.FridgeItemDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface FridgeItemService {

    FridgeItemDTO createFridgeItem(FridgeItemDTO itemDTO);
    FridgeItemDTO getFridgeItemById(UUID id);
    Page<FridgeItemDTO> getFridgeItems(Pageable pageable);
    FridgeItemDTO updateFridgeItem(UUID id, FridgeItemDTO itemDTO);
    void deleteFridgeItem(UUID id);
}
