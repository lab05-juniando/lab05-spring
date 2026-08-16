package com.lab05.finances.shoppingItem.repository;

import com.lab05.finances.shoppingItem.entity.shoppingItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ShoppingItemRepository extends JpaRepository<shoppingItem, UUID> {

    @Query("SELECT i FROM shoppingItem i WHERE " +
           "(:status IS NULL OR i.itemStatus = :status) AND " +
           "(:categoria IS NULL OR i.itemCategory = :categoria)")
    List<shoppingItem> findByFiltros(@Param("status") Boolean status,
                                      @Param("categoria") String categoria);
}
