package com.inventory.inventory.controller;

import com.inventory.inventory.service.impl.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class InventoryController {

    private final ProductServiceImpl service;

    @PostMapping(path = "/create-inventory")
    public void createInventory(@RequestBody String request) {
        service.createInventory(request);
    }

    @PostMapping(path = "/increase-inventory-count")
    public void increaseProductCount(@RequestBody String request) {
        service.increaseProductCount(request);
    }

    @PostMapping(path = "/decrease-inventory-count")
    public void decreaseProductCount(@RequestBody String request) {
        service.decreaseProductCount(request);
    }

    @PostMapping(path = "/get-inventory-count")
    public ResponseEntity<Long> getProductCount(@RequestBody String request) {
        return ResponseEntity.ok(service.getProductCount(request));
    }
}
