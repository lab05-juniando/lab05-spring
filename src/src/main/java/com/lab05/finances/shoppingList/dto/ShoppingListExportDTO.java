package com.lab05.finances.shoppingList.dto;

import com.lab05.finances.shoppingList.entity.shoppingList;

import java.util.UUID;

public class ShoppingListExportDTO {

    private UUID id;
    private String shoppingListName;
    private Boolean shoppingListStatus;
    private UUID companyId;
    private int totalItens;

    public ShoppingListExportDTO(shoppingList list) {
        this.id = list.getId();
        this.shoppingListName = list.getShoppingListName();
        this.shoppingListStatus = list.getShoppingListStatus();
        this.companyId = list.getCompanyId();
        this.totalItens = list.getItems() != null ? list.getItems().size() : 0;
    }

    public UUID getId() {
        return id;
    }

    public String getShoppingListName() {
        return shoppingListName;
    }

    public Boolean getShoppingListStatus() {
        return shoppingListStatus;
    }

    public UUID getCompanyId() {
        return companyId;
    }

    public int getTotalItens() {
        return totalItens;
    }
}
