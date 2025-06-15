package com.inventory.inventory.model.repository;

import com.inventory.inventory.model.entity.InventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryRepository extends JpaRepository<InventoryEntity, Long> {

    InventoryEntity findByProductName(String productName);

}
