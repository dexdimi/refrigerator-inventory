package com.hylastix.fridgeservice.controller;

import com.hylastix.fridgeservice.dto.FridgeItemDTO;
import com.hylastix.fridgeservice.service.FridgeItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/fridge-items")
@RequiredArgsConstructor
public class FridgeItemController {

    private final FridgeItemService service;

    @PostMapping
    public ResponseEntity<FridgeItemDTO> createItem(@RequestBody FridgeItemDTO itemDTO) {
        return ResponseEntity.ok(service.createFridgeItem(itemDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FridgeItemDTO> getItem(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getFridgeItemById(id));
    }

    @GetMapping
    public ResponseEntity<Page<FridgeItemDTO>> getAllItems(Pageable pageable) {
        return ResponseEntity.ok(service.getFridgeItems(pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FridgeItemDTO> updateItem(@PathVariable UUID id, @RequestBody FridgeItemDTO itemDTO) {
        return ResponseEntity.ok(service.updateFridgeItem(id, itemDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable UUID id) {
        service.deleteFridgeItem(id);
        return ResponseEntity.noContent().build();
    }
}
