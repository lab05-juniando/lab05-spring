package com.lab05.finances.shoppingItem.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class shoppingItemResponseDTO {

    private UUID id;
    private UUID shoppingListId;
    private String itemName;
    private BigDecimal itemValue;
    private String itemDescription;
    private String itemCategory;
    private Boolean itemStatus;
    private LocalDate purchaseDate;
    private UUID transactionId;

    public shoppingItemResponseDTO() {
    }

    public shoppingItemResponseDTO(UUID id, UUID shoppingListId, String itemName, BigDecimal itemValue,
                                   String itemDescription, String itemCategory,
                                   Boolean itemStatus, LocalDate purchaseDate, UUID transactionId) {
        this.id = id;
        this.shoppingListId = shoppingListId;
        this.itemName = itemName;
        this.itemValue = itemValue;
        this.itemDescription = itemDescription;
        this.itemCategory = itemCategory;
        this.itemStatus = itemStatus;
        this.purchaseDate = purchaseDate;
        this.transactionId = transactionId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getShoppingListId() {
        return shoppingListId;
    }

    public void setShoppingListId(UUID shoppingListId) {
        this.shoppingListId = shoppingListId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public BigDecimal getItemValue() {
        return itemValue;
    }

    public void setItemValue(BigDecimal itemValue) {
        this.itemValue = itemValue;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }

    public Boolean getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(Boolean itemStatus) {
        this.itemStatus = itemStatus;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(UUID transactionId) {
        this.transactionId = transactionId;
    }
}