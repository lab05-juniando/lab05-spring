package com.lab05.finances.shoppingItem.dto;

import com.lab05.finances.shoppingItem.entity.shoppingItem;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class ShoppingItemExportDTO {

    private UUID id;
    private String shoppingListName;
    private String itemName;
    private BigDecimal itemValue;
    private String itemDescription;
    private String itemCategory;
    private Boolean itemStatus;
    private LocalDate purchaseDate;

    public ShoppingItemExportDTO(shoppingItem item) {
        this.id = item.getId();
        this.shoppingListName = item.getShoppingList() != null
                ? item.getShoppingList().getShoppingListName()
                : "";
        this.itemName = item.getItemName();
        this.itemValue = item.getItemValue();
        this.itemDescription = item.getItemDescription();
        this.itemCategory = item.getItemCategory();
        this.itemStatus = item.getItemStatus();
        this.purchaseDate = item.getPurchaseDate();
    }

    public UUID getId() {
        return id;
    }

    public String getShoppingListName() {
        return shoppingListName;
    }

    public String getItemName() {
        return itemName;
    }

    public BigDecimal getItemValue() {
        return itemValue;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public Boolean getItemStatus() {
        return itemStatus;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }
}
