package com.lab05.finances.shoppingItem.repository;

import com.lab05.finances.shoppingItem.entity.shoppingItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface shoppingItemRepository extends JpaRepository<shoppingItemEntity, UUID> {

    List<shoppingItemEntity> findByShoppingListId(UUID shoppingListId);
}