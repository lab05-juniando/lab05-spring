package com.lab05.finances.shoppingList.dto;

import java.util.UUID;

public class shoppingListRequestDTO {

    private UUID companyId;

    public shoppingListRequestDTO() {
    }

    public shoppingListRequestDTO(UUID companyId) {
        this.companyId = companyId;
    }

    public UUID getCompanyId() {
        return companyId;
    }

    public void setCompanyId(UUID companyId) {
        this.companyId = companyId;
    }
}