package com.lab05.finances.shoppingList.repository;

import com.lab05.finances.shoppingList.entity.shoppingList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ShoppingListRepository extends JpaRepository<shoppingList, UUID> {

    @Query("SELECT l FROM shoppingList l WHERE " +
           "(:status IS NULL OR l.shoppingListStatus = :status)")
    List<shoppingList> findByFiltros(@Param("status") Boolean status);
}
