package com.inventory.inventory.service.impl;

import com.inventory.inventory.model.entity.InventoryEntity;
import com.inventory.inventory.model.repository.InventoryRepository;
import com.inventory.inventory.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final InventoryRepository repository;

    @Override
    public void createInventory(String productName) {
        InventoryEntity entity = new InventoryEntity();
        entity.setProductName(productName);
        entity.setProductCount(0L);
        repository.save(entity);
    }

    @Override
    public void increaseProductCount(String productName) {
        InventoryEntity entity = repository.findByProductName(productName);
        Long count = entity.getProductCount();
        count += 1L;
        entity.setProductCount(count);
        repository.save(entity);
    }

    @Override
    public void decreaseProductCount(String productName) {
        InventoryEntity entity = repository.findByProductName(productName);
        Long count = entity.getProductCount();
        count -= 1L;
        entity.setProductCount(count);
        repository.save(entity);
    }

    @Override
    public Long getProductCount(String productName) {
        InventoryEntity entity = repository.findByProductName(productName);
        Long count = entity.getProductCount();
        return count;
    }
}
