package com.hylastix.fridgeservice.repository;

import com.hylastix.fridgeservice.model.FridgeItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface FridgeItemRepository  extends JpaRepository<FridgeItem, UUID>, JpaSpecificationExecutor<FridgeItem> {
}
