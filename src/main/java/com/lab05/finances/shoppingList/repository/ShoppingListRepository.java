package com.lab05.finances.shoppingList.repository;

import com.lab05.finances.shoppingList.entity.shoppingList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ShoppingListRepository extends JpaRepository<shoppingList, UUID> {

    List<shoppingList> findByCompanyId(UUID companyId);
}