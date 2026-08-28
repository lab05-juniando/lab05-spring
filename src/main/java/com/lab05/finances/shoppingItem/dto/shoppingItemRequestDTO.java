package com.lab05.finances.shoppingItem.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class shoppingItemRequestDTO {

    @NotNull(message = "shoppingListId é obrigatório")
    private UUID shoppingListId;
    @NotNull(message = "itemName é obrigatório")
    @Size(min = 1, max = 255, message = "itemName deve ter entre 1 e 255 caracteres")
    private String itemName;
    @NotNull(message = "itemValue é obrigatório")
    @DecimalMin(value = "0.00", inclusive = true, message = "itemValue não pode ser negativo")
    private BigDecimal itemValue;
    private String itemDescription;
    private String itemCategory;
    private Boolean itemStatus;
    private LocalDate purchaseDate;

    public shoppingItemRequestDTO() {
    }

    public shoppingItemRequestDTO(UUID shoppingListId, String itemName, BigDecimal itemValue,
                                  String itemDescription, String itemCategory,
                                  Boolean itemStatus, LocalDate purchaseDate) {
        this.shoppingListId = shoppingListId;
        this.itemName = itemName;
        this.itemValue = itemValue;
        this.itemDescription = itemDescription;
        this.itemCategory = itemCategory;
        this.itemStatus = itemStatus;
        this.purchaseDate = purchaseDate;
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
}