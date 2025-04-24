package com.hylastix.fridgeservice.service.impl;

import com.hylastix.fridgeservice.dto.FridgeItemDTO;
import com.hylastix.fridgeservice.exceptions.custom.ResourceNotFoundException;
import com.hylastix.fridgeservice.filter.FridgeSpecifications;
import com.hylastix.fridgeservice.model.Category;
import com.hylastix.fridgeservice.model.FridgeItem;
import com.hylastix.fridgeservice.repository.FridgeItemRepository;
import com.hylastix.fridgeservice.service.FridgeItemService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FridgeItemServiceImpl implements FridgeItemService {

    private final FridgeItemRepository repository;
    private final ModelMapper mapper;

    private static final Logger logger = LoggerFactory.getLogger(FridgeItemServiceImpl.class);

    @Override
    @Transactional
    public FridgeItemDTO createFridgeItem(FridgeItemDTO fridgeItemDTO) {
        if(fridgeItemDTO.getTimeStored()==null){
            fridgeItemDTO.setTimeStored(LocalDateTime.now());
        }

        FridgeItem fridgeItem = mapper.map(fridgeItemDTO, FridgeItem.class);

        FridgeItem createdFridgeItem = repository.save(fridgeItem);
        logger.info("FridgeItemService.createFridgeItem successfully created FridgeItem: {}", createdFridgeItem.getName());
        return mapper.map(createdFridgeItem, FridgeItemDTO.class);
    }

    @Override
    public FridgeItemDTO getFridgeItemById(UUID id) {
        FridgeItem fridgeItem = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("FridgeItem not found with ID: " + id));

        logger.info("FridgeItemService.getFridgeItemById retrieved FridgeItem: {}", fridgeItem.getName());

        return mapper.map(fridgeItem, FridgeItemDTO.class);
    }

    @Override
    public Page<FridgeItemDTO> getFridgeItems(String name, Category category, Pageable pageable) {
        Specification<FridgeItem> specification = FridgeSpecifications.withFilters(name, category);

        return repository.findAll(specification, pageable)
                .map(fridgeItem -> {
                    logger.info("FridgeItemService.getFridgeItems retrieved FridgeItem: {}", fridgeItem.getName());
                    return mapper.map(fridgeItem, FridgeItemDTO.class);
                });
    }

    @Override
    @Transactional
    public FridgeItemDTO updateFridgeItem(UUID id, FridgeItemDTO fridgeItemDTO) {
        FridgeItem fridgeItem = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("FridgeItem not found with ID: " + id));

        mapper.map(fridgeItemDTO, fridgeItem);

        FridgeItem updatedItem = repository.save(fridgeItem);
        logger.info("Updated FridgeItem with ID: {}, Name: {}, Best Before: {}",
                updatedItem.getId(), updatedItem.getName(), updatedItem.getBestBeforeDate());


        return mapper.map(repository.save(fridgeItem), FridgeItemDTO.class);
    }

    @Override
    @Transactional
    public void deleteFridgeItem(UUID id) {
       if(!repository.existsById(id)){
            throw new ResourceNotFoundException("FridgeItem not found with ID: " + id);
       }

       repository.deleteById(id);
       logger.info("FridgeItemService.getFridgeItems successful deletion of FridgeItem with id: {}", id);
    }
}
