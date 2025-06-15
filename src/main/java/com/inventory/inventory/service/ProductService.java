package com.inventory.inventory.service;

public interface ProductService {

    void createInventory(String productName);

    void increaseProductCount(String productName);

    void decreaseProductCount(String productName);

    Long getProductCount(String productName);
}
