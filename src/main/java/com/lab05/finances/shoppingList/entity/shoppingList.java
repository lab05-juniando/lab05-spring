package com.lab05.finances.shoppingList.entity;

import com.lab05.finances.shoppingItem.entity.shoppingItem;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "shopping_list")
public class shoppingList {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_shopping_list", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "shopping_list_name", nullable = false)
    private String shoppingListName;

    @Column(name = "shopping_list_status", nullable = false)
    private Boolean shoppingListStatus;

    @Column(name = "id_company", nullable = false)
    private UUID companyId;

    @OneToMany(mappedBy = "shoppingList", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<shoppingItem> items = new ArrayList<>();

    public shoppingList() {
    }

    public shoppingList(String shoppingListName, Boolean shoppingListStatus, UUID companyId) {
        this.shoppingListName = shoppingListName;
        this.shoppingListStatus = shoppingListStatus;
        this.companyId = companyId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getShoppingListName() {
        return shoppingListName;
    }

    public void setShoppingListName(String shoppingListName) {
        this.shoppingListName = shoppingListName;
    }

    public Boolean getShoppingListStatus() {
        return shoppingListStatus;
    }

    public void setShoppingListStatus(Boolean shoppingListStatus) {
        this.shoppingListStatus = shoppingListStatus;
    }

    public UUID getCompanyId() {
        return companyId;
    }

    public void setCompanyId(UUID companyId) {
        this.companyId = companyId;
    }

    public List<shoppingItem> getItems() {
        return items;
    }

    public void setItems(List<shoppingItem> items) {
        this.items = items;
    }
}