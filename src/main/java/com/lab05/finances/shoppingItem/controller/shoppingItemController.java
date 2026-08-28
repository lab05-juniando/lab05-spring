package com.lab05.finances.shoppingItem.controller;

import com.lab05.finances.shoppingItem.dto.shoppingItemRequestDTO;
import com.lab05.finances.shoppingItem.dto.shoppingItemResponseDTO;
import com.lab05.finances.shoppingItem.service.shoppingItemService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/shopping-items")
public class shoppingItemController {

    private final shoppingItemService shoppingItemService;

    public shoppingItemController(shoppingItemService shoppingItemService) {
        this.shoppingItemService = shoppingItemService;
    }

    @PostMapping
    public ResponseEntity<shoppingItemResponseDTO> create(@Valid @RequestBody shoppingItemRequestDTO dto) {
        shoppingItemResponseDTO created = shoppingItemService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<shoppingItemResponseDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(shoppingItemService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<shoppingItemResponseDTO>> findAll(
            @RequestParam(required = false) UUID shoppingListId) {

        if (shoppingListId != null) {
            return ResponseEntity.ok(shoppingItemService.findByShoppingListId(shoppingListId));
        }
        return ResponseEntity.ok(shoppingItemService.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<shoppingItemResponseDTO> update(
            @PathVariable UUID id,
            @Valid @RequestBody shoppingItemRequestDTO dto) {
        return ResponseEntity.ok(shoppingItemService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        shoppingItemService.delete(id);
        return ResponseEntity.noContent().build();
    }
}