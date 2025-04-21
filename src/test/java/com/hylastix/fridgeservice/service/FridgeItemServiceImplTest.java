package com.hylastix.fridgeservice.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import com.hylastix.fridgeservice.dto.FridgeItemDTO;
import com.hylastix.fridgeservice.model.Category;
import com.hylastix.fridgeservice.model.FridgeItem;
import com.hylastix.fridgeservice.model.Unit;
import com.hylastix.fridgeservice.repository.FridgeItemRepository;
import com.hylastix.fridgeservice.exceptions.custom.ResourceNotFoundException;

import com.hylastix.fridgeservice.service.impl.FridgeItemServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@ExtendWith(MockitoExtension.class)
public class FridgeItemServiceImplTest {

    @Mock
    private FridgeItemRepository repository;

    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private FridgeItemServiceImpl service;

    private UUID id;
    private FridgeItemDTO dto;
    private FridgeItem entity;

    @BeforeEach
    void setUp() {
        id = UUID.randomUUID();
        dto = FridgeItemDTO.builder()
                .id(id)
                .name("Milk")
                .category(Category.DAIRY)
                .unit(Unit.LITRES)
                .quantity(2)
                .timeStored(LocalDateTime.of(2025, 4, 20, 10, 0))
                .bestBeforeDate(LocalDate.of(2025, 4, 25))
                .build();

        entity = new FridgeItem();
        entity.setId(id);
        entity.setName("Milk");
        entity.setCategory(Category.DAIRY);
        entity.setUnit(Unit.LITRES);
        entity.setQuantity(2);
        entity.setTimeStored(LocalDateTime.of(2025, 4, 20, 10, 0));
        entity.setBestBeforeDate(LocalDate.of(2025, 4, 25));
    }

    @Test
    void createFridgeItem_success() {
        when(mapper.map(dto, FridgeItem.class)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.map(entity, FridgeItemDTO.class)).thenReturn(dto);

        FridgeItemDTO result = service.createFridgeItem(dto);

        assertEquals(dto, result);
        verify(mapper).map(dto, FridgeItem.class);
        verify(repository).save(entity);
        verify(mapper).map(entity, FridgeItemDTO.class);
    }

    @Test
    void getFridgeItemById_found() {
        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.map(entity, FridgeItemDTO.class)).thenReturn(dto);

        FridgeItemDTO result = service.getFridgeItemById(id);

        assertEquals(dto, result);
        verify(repository).findById(id);
    }

    @Test
    void getFridgeItemById_notFound() {
        when(repository.findById(id)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.getFridgeItemById(id));
        verify(repository).findById(id);
    }

    @Test
    void getFridgeItems_success() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<FridgeItem> page = new PageImpl<>(List.of(entity));
        when(repository.findAll(any(Specification.class), eq(pageable))).thenReturn(page);
        when(mapper.map(entity, FridgeItemDTO.class)).thenReturn(dto);

        Page<FridgeItemDTO> result = service.getFridgeItems("Milk", Category.DAIRY, pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals(dto, result.getContent().get(0));
        verify(repository).findAll(any(Specification.class), eq(pageable));
    }

    @Test
    void updateFridgeItem_success() {
        FridgeItemDTO updateDto = FridgeItemDTO.builder()
                .id(id)
                .name("Skim Milk")
                .category(Category.DAIRY)
                .unit(Unit.LITRES)
                .quantity(1)
                .timeStored(LocalDateTime.of(2025, 4, 21, 11, 0))
                .bestBeforeDate(LocalDate.of(2025, 4, 28))
                .build();

        FridgeItem updatedEntity = new FridgeItem();
        updatedEntity.setId(id);
        updatedEntity.setName("Skim Milk");
        updatedEntity.setCategory(Category.DAIRY);
        updatedEntity.setUnit(Unit.LITRES);
        updatedEntity.setQuantity(1);
        updatedEntity.setTimeStored(LocalDateTime.of(2025, 4, 21, 11, 0));
        updatedEntity.setBestBeforeDate(LocalDate.of(2025, 4, 28));

        when(repository.findById(id)).thenReturn(Optional.of(entity));

        doAnswer(invocation -> {
            FridgeItemDTO src = invocation.getArgument(0);
            FridgeItem targ = invocation.getArgument(1);
            targ.setName(src.getName());
            targ.setCategory(src.getCategory());
            targ.setUnit(src.getUnit());
            targ.setQuantity(src.getQuantity());
            targ.setTimeStored(src.getTimeStored());
            targ.setBestBeforeDate(src.getBestBeforeDate());
            return null;
        }).when(mapper).map(updateDto, entity);

        when(repository.save(entity)).thenReturn(updatedEntity);
        when(mapper.map(updatedEntity, FridgeItemDTO.class)).thenReturn(updateDto);

        FridgeItemDTO result = service.updateFridgeItem(id, updateDto);

        assertEquals("Skim Milk", result.getName());
        verify(repository, times(2)).save(entity);
        verify(mapper).map(updateDto, entity);
        verify(mapper).map(updatedEntity, FridgeItemDTO.class);
    }

    @Test
    void updateFridgeItem_notFound() {
        when(repository.findById(id)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.updateFridgeItem(id, dto));
        verify(repository).findById(id);
    }

    @Test
    void deleteFridgeItem_success() {
        when(repository.existsById(id)).thenReturn(true);

        service.deleteFridgeItem(id);

        verify(repository).existsById(id);
        verify(repository).deleteById(id);
    }

    @Test
    void deleteFridgeItem_notFound() {
        when(repository.existsById(id)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> service.deleteFridgeItem(id));
        verify(repository).existsById(id);
    }
}
