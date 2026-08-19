package com.lab05.finances.shoppingItem.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import com.lab05.finances.shoppingList.entity.shoppingListEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "shopping_item")
public class shoppingItemEntity {

    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_item", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_shopping_list", nullable = false)
    private shoppingListEntity shoppingList;

    @Column(name = "item_name", nullable = false)
    private String itemName;

    @Column(name = "item_value", nullable = false)
    private BigDecimal itemValue;

    @Column(name = "item_description")
    private String itemDescription;

    @Column(name = "item_category")
    private String itemCategory;

    @Column(name = "item_status", nullable = false)
    private Boolean itemStatus;

    @Column(name = "purchase_date")
    private LocalDate purchaseDate;

    @Column(name = "id_transaction")
    private UUID transactionId;

    public shoppingItemEntity() {
    }

    public shoppingItemEntity(shoppingListEntity shoppingList, String itemName, BigDecimal itemValue,
                              String itemDescription, String itemCategory,
                              Boolean itemStatus, LocalDate purchaseDate) {
        this.shoppingList = shoppingList;
        this.itemName = itemName;
        this.itemValue = itemValue;
        this.itemDescription = itemDescription;
        this.itemCategory = itemCategory;
        this.itemStatus = itemStatus;
        this.purchaseDate = purchaseDate;
    }
    public void marcarComoComprado() {
    this.itemStatus = true;
    this.purchaseDate = LocalDate.now();
 }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public shoppingListEntity getShoppingList() {
        return shoppingList;
    }

    public void setShoppingList(shoppingListEntity shoppingList) {
        this.shoppingList = shoppingList;
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