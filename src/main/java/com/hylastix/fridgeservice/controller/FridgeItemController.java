package com.hylastix.fridgeservice.controller;

import com.hylastix.fridgeservice.dto.CategoryDTO;
import com.hylastix.fridgeservice.dto.FridgeItemDTO;
import com.hylastix.fridgeservice.dto.UnitDTO;
import com.hylastix.fridgeservice.model.Category;
import com.hylastix.fridgeservice.model.Unit;
import com.hylastix.fridgeservice.service.FridgeItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Tag(name = "Refrigerator Inventory tracker API", description = "Operations related to tracking of refrigerator inventory")
@RestController
@RequestMapping("/api/fridge-items")
@RequiredArgsConstructor
public class FridgeItemController {
    //http://localhost:8080/api/fridge-items/units

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

    @Operation(summary = "Get all Units elements", description = "Returns a list of Units to fill up the Unit select-box")
    @GetMapping("/units")
    public List<UnitDTO> getUnits() {
        return Arrays.stream(Unit.values())
                .map(unit -> new UnitDTO(unit.name(), unit.getLabel()))
                .toList();
    }

    @Operation(summary = "Get all Categories elements", description = "Returns a list of Categories to fill up the Category select-box")
    @GetMapping("/categories")
    public List<CategoryDTO> getCategories() {
        System.out.println(Arrays.stream(Category.values())
                .map(category -> new CategoryDTO(category.name(), category.getLabel()))
                .toList());

        return Arrays.stream(Category.values())
                .map(category -> new CategoryDTO(category.name(), category.getLabel()))
                .toList();

    }
}
