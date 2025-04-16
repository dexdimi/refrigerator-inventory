package com.hylastix.fridgeservice.service.impl;

import com.hylastix.fridgeservice.dto.FridgeItemDTO;
import com.hylastix.fridgeservice.model.FridgeItem;
import com.hylastix.fridgeservice.repository.FridgeItemRepository;
import com.hylastix.fridgeservice.service.FridgeItemService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FridgeItemServiceImpl implements FridgeItemService {

    private final FridgeItemRepository repository;
    private final ModelMapper mapper;

    @Override
    public FridgeItemDTO createFridgeItem(FridgeItemDTO itemDTO) {
        FridgeItem entity = mapper.map(itemDTO, FridgeItem.class);
        return mapper.map(repository.save(entity), FridgeItemDTO.class);
    }

    @Override
    public FridgeItemDTO getFridgeItemById(UUID id) {
        FridgeItem item = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found"));
        return mapper.map(item, FridgeItemDTO.class);
    }

    @Override
    public Page<FridgeItemDTO> getFridgeItems(Pageable pageable) {

        return repository.findAll(pageable)
                .map(item -> mapper.map(item, FridgeItemDTO.class));


    }

    @Override
    public FridgeItemDTO updateFridgeItem(UUID id, FridgeItemDTO itemDTO) {
        FridgeItem item = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found"));
        mapper.map(itemDTO, item);
        return mapper.map(repository.save(item), FridgeItemDTO.class);
    }

    @Override
    public void deleteFridgeItem(UUID id) {
        repository.deleteById(id);
    }
}
