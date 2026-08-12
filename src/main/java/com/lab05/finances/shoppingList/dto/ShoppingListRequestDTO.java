package com.lab05.finances.shoppingList.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public class ShoppingListRequestDTO {

    @NotBlank(message = "O nome da lista é obrigatório")
    private String shoppingListName;

    private Boolean shoppingListStatus;

    @NotNull(message = "O id da empresa é obrigatório")
    private UUID companyId;

    public ShoppingListRequestDTO() {
    }

    public String getShoppingListName() { return shoppingListName; }
    public void setShoppingListName(String shoppingListName) { this.shoppingListName = shoppingListName; }

    public Boolean getShoppingListStatus() { return shoppingListStatus; }
    public void setShoppingListStatus(Boolean shoppingListStatus) { this.shoppingListStatus = shoppingListStatus; }

    public UUID getCompanyId() { return companyId; }
    public void setCompanyId(UUID companyId) { this.companyId = companyId; }
}