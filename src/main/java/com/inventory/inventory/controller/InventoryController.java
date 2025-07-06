package com.inventory.inventory.controller;

import com.inventory.inventory.service.impl.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class InventoryController {

    private final ProductServiceImpl service;

    @PostMapping(path = "/create-inventory")
    public ResponseEntity<?> createInventory(@RequestBody String request) {
        service.createInventory(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "/increase-inventory-count")
    public ResponseEntity<?> increaseProductCount(@RequestBody String request) {
        service.increaseProductCount(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "/decrease-inventory-count")
    public ResponseEntity<?> decreaseProductCount(@RequestBody String request) {
        service.decreaseProductCount(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "/get-inventory-count")
    public ResponseEntity<Long> getProductCount(@RequestBody String request) {
        Long count = service.getProductCount(request);
        return ResponseEntity.ok(count);
    }
}
