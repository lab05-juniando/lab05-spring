package com.lab05.finances.shoppingList.dto;

import com.lab05.finances.shoppingItem.dto.shoppingItemResponseDTO;

import java.util.List;
import java.util.UUID;

public class shoppingListResponseDTO {

    private UUID id;
    private UUID companyId;
    private List<shoppingItemResponseDTO> items;

    public shoppingListResponseDTO() {
    }

    public shoppingListResponseDTO(UUID id, UUID companyId, List<shoppingItemResponseDTO> items) {
        this.id = id;
        this.companyId = companyId;
        this.items = items;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getCompanyId() {
        return companyId;
    }

    public void setCompanyId(UUID companyId) {
        this.companyId = companyId;
    }

    public List<shoppingItemResponseDTO> getItems() {
        return items;
    }

    public void setItems(List<shoppingItemResponseDTO> items) {
        this.items = items;
    }
}