package com.lab05.finances.shoppingList.dto;

import java.util.UUID;

public class ShoppingListResponseDTO {

    private UUID id;
    private String shoppingListName;
    private Boolean shoppingListStatus;
    private UUID companyId;
    private int totalItems;

    public ShoppingListResponseDTO() {
    }

    public ShoppingListResponseDTO(UUID id, String shoppingListName, Boolean shoppingListStatus,
                                   UUID companyId, int totalItems) {
        this.id = id;
        this.shoppingListName = shoppingListName;
        this.shoppingListStatus = shoppingListStatus;
        this.companyId = companyId;
        this.totalItems = totalItems;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getShoppingListName() { return shoppingListName; }
    public void setShoppingListName(String shoppingListName) { this.shoppingListName = shoppingListName; }

    public Boolean getShoppingListStatus() { return shoppingListStatus; }
    public void setShoppingListStatus(Boolean shoppingListStatus) { this.shoppingListStatus = shoppingListStatus; }

    public UUID getCompanyId() { return companyId; }
    public void setCompanyId(UUID companyId) { this.companyId = companyId; }

    public int getTotalItems() { return totalItems; }
    public void setTotalItems(int totalItems) { this.totalItems = totalItems; }
}