package com.lab05.finances.shoppingList.service;

import com.lab05.finances.shoppingItem.dto.shoppingItemResponseDTO;
import com.lab05.finances.shoppingItem.entity.shoppingItemEntity;
import com.lab05.finances.shoppingList.dto.shoppingListRequestDTO;
import com.lab05.finances.shoppingList.dto.shoppingListResponseDTO;
import com.lab05.finances.shoppingList.entity.shoppingListEntity;
import com.lab05.finances.shoppingList.repository.shoppingListRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class shoppingListService {

    private final shoppingListRepository shoppingListRepository;

    public shoppingListService(shoppingListRepository shoppingListRepository) {
        this.shoppingListRepository = shoppingListRepository;
    }

    @Transactional
    public shoppingListResponseDTO create(shoppingListRequestDTO dto) {
        shoppingListEntity entity = new shoppingListEntity(dto.getCompanyId());
        shoppingListEntity saved = shoppingListRepository.save(entity);
        return toResponseDTO(saved);
    }

    @Transactional(readOnly = true)
    public shoppingListResponseDTO findById(UUID id) {
        shoppingListEntity entity = shoppingListRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shopping list não encontrada com id: " + id));
        return toResponseDTO(entity);
    }

    @Transactional(readOnly = true)
    public List<shoppingListResponseDTO> findAll() {
        return shoppingListRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<shoppingListResponseDTO> findByCompanyId(UUID companyId) {
        return shoppingListRepository.findByCompanyId(companyId)
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public shoppingListResponseDTO update(UUID id, shoppingListRequestDTO dto) {
        shoppingListEntity entity = shoppingListRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shopping list não encontrada com id: " + id));

        entity.setCompanyId(dto.getCompanyId());

        shoppingListEntity updated = shoppingListRepository.save(entity);
        return toResponseDTO(updated);
    }

    @Transactional
    public void delete(UUID id) {
        if (!shoppingListRepository.existsById(id)) {
            throw new RuntimeException("Shopping list não encontrada com id: " + id);
        }
        shoppingListRepository.deleteById(id);
    }

    private shoppingListResponseDTO toResponseDTO(shoppingListEntity entity) {
        List<shoppingItemResponseDTO> items = entity.getItems() == null
                ? List.of()
                : entity.getItems().stream()
                .map(this::toItemResponseDTO)
                .collect(Collectors.toList());

        return new shoppingListResponseDTO(entity.getId(), entity.getCompanyId(), items);
    }

    private shoppingItemResponseDTO toItemResponseDTO(shoppingItemEntity item) {
        return new shoppingItemResponseDTO(
                item.getId(),
                item.getShoppingList().getId(),
                item.getItemName(),
                item.getItemValue(),
                item.getItemDescription(),
                item.getItemCategory(),
                item.getItemStatus(),
                item.getPurchaseDate(),
                item.getTransactionId()
        );
    }
}