package com.hylastix.fridgeservice.controller;

import com.hylastix.fridgeservice.dto.FridgeItemDTO;
import com.hylastix.fridgeservice.model.Category;
import com.hylastix.fridgeservice.service.FridgeItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Refrigerator Inventory tracker API", description = "Operations related to tracking of refrigerator inventory")
@RestController
@RequestMapping("/api/fridge-items")
@RequiredArgsConstructor
public class FridgeItemController {

    private final FridgeItemService service;

    @Operation(summary = "Create a new Fridge item", description = "Creates and returns a new Fridge item")
    @PostMapping
    public ResponseEntity<FridgeItemDTO> createFridgeItem(@RequestBody FridgeItemDTO itemDTO) {
        return ResponseEntity.ok(service.createFridgeItem(itemDTO));
    }

    @Operation(summary = "Get a Fridge item by ID", description = "Returns the Fridge item with the given UUID")
    @GetMapping("/{id}")
    public ResponseEntity<FridgeItemDTO> getFridgeItem(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getFridgeItemById(id));
    }

    @Operation(summary = "Get all Fridge items", description = "Returns a list of Fridge items; can filter by Name and-or Category if needed")
    @GetMapping
    public ResponseEntity<Page<FridgeItemDTO>> getFridgeItems(Pageable pageable, @RequestParam(required = false) String name,
                                                              @RequestParam(required = false) Category category) {
        return ResponseEntity.ok(service.getFridgeItems(name, category, pageable));
    }

    @Operation(summary = "Update an existing Fridge item", description = "Updates the Fridge item identified by the UUID in the URL")
    @PutMapping("/{id}")
    public ResponseEntity<FridgeItemDTO> updateFridgeItem(@PathVariable UUID id, @RequestBody FridgeItemDTO itemDTO) {
        return ResponseEntity.ok(service.updateFridgeItem(id, itemDTO));
    }

    @Operation(summary = "Delete a Fridge item", description = "Deletes the Fridge item with the given UUID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFridgeItem(@PathVariable UUID id) {
        service.deleteFridgeItem(id);
        return ResponseEntity.noContent().build();
    }
}
