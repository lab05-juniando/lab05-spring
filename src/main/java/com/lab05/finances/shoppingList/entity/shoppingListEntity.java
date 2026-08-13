package com.lab05.finances.shoppingList.entity;

import com.lab05.finances.shoppingItem.entity.shoppingItemEntity;

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
public class shoppingListEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_shopping_list", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "id_company", nullable = false)
    private UUID companyId;

    @OneToMany(mappedBy = "shoppingList", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<shoppingItemEntity> items = new ArrayList<>();

    public shoppingListEntity() {
    }

    public shoppingListEntity(UUID companyId) {
        this.companyId = companyId;
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

    public List<shoppingItemEntity> getItems() {
        return items;
    }

    public void setItems(List<shoppingItemEntity> items) {
        this.items = items;
    }
}