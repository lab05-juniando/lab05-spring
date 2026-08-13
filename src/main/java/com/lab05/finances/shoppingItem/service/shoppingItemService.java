package com.lab05.finances.shoppingItem.service;

import com.lab05.finances.shoppingItem.dto.shoppingItemRequestDTO;
import com.lab05.finances.shoppingItem.dto.shoppingItemResponseDTO;
import com.lab05.finances.shoppingItem.entity.shoppingItemEntity;
import com.lab05.finances.shoppingItem.repository.shoppingItemRepository;
import com.lab05.finances.shoppingList.entity.shoppingListEntity;
import com.lab05.finances.shoppingList.repository.shoppingListRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class shoppingItemService {

    private final shoppingItemRepository shoppingItemRepository;
    private final shoppingListRepository shoppingListRepository;

    public shoppingItemService(shoppingItemRepository shoppingItemRepository,
                               shoppingListRepository shoppingListRepository) {
        this.shoppingItemRepository = shoppingItemRepository;
        this.shoppingListRepository = shoppingListRepository;
    }

    @Transactional
    public shoppingItemResponseDTO create(shoppingItemRequestDTO dto) {
        shoppingListEntity list = shoppingListRepository.findById(dto.getShoppingListId())
                .orElseThrow(() -> new RuntimeException(
                        "Shopping list não encontrada com id: " + dto.getShoppingListId()));

        shoppingItemEntity entity = new shoppingItemEntity(
                list,
                dto.getItemName(),
                dto.getItemValue(),
                dto.getItemDescription(),
                dto.getItemCategory(),
                dto.getItemStatus(),
                dto.getPurchaseDate()
        );

        shoppingItemEntity saved = shoppingItemRepository.save(entity);
        return toResponseDTO(saved);
    }

    @Transactional(readOnly = true)
    public shoppingItemResponseDTO findById(UUID id) {
        shoppingItemEntity entity = shoppingItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item não encontrado com id: " + id));
        return toResponseDTO(entity);
    }

    @Transactional(readOnly = true)
    public List<shoppingItemResponseDTO> findAll() {
        return shoppingItemRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<shoppingItemResponseDTO> findByShoppingListId(UUID shoppingListId) {
        return shoppingItemRepository.findByShoppingListId(shoppingListId)
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public shoppingItemResponseDTO update(UUID id, shoppingItemRequestDTO dto) {
        shoppingItemEntity entity = shoppingItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item não encontrado com id: " + id));

        if (dto.getShoppingListId() != null
                && !dto.getShoppingListId().equals(entity.getShoppingList().getId())) {
            shoppingListEntity list = shoppingListRepository.findById(dto.getShoppingListId())
                    .orElseThrow(() -> new RuntimeException(
                            "Shopping list não encontrada com id: " + dto.getShoppingListId()));
            entity.setShoppingList(list);
        }

        entity.setItemName(dto.getItemName());
        entity.setItemValue(dto.getItemValue());
        entity.setItemDescription(dto.getItemDescription());
        entity.setItemCategory(dto.getItemCategory());
        entity.setItemStatus(dto.getItemStatus());
        entity.setPurchaseDate(dto.getPurchaseDate());

        shoppingItemEntity updated = shoppingItemRepository.save(entity);
        return toResponseDTO(updated);
    }

    @Transactional
    public void delete(UUID id) {
        if (!shoppingItemRepository.existsById(id)) {
            throw new RuntimeException("Item não encontrado com id: " + id);
        }
        shoppingItemRepository.deleteById(id);
    }

    private shoppingItemResponseDTO toResponseDTO(shoppingItemEntity entity) {
        return new shoppingItemResponseDTO(
                entity.getId(),
                entity.getShoppingList().getId(),
                entity.getItemName(),
                entity.getItemValue(),
                entity.getItemDescription(),
                entity.getItemCategory(),
                entity.getItemStatus(),
                entity.getPurchaseDate(),
                entity.getTransactionId()
        );
    }
}