package com.lab05.finances.shoppingList.repository;

import com.lab05.finances.shoppingList.entity.shoppingListEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface shoppingListRepository extends JpaRepository<shoppingListEntity, UUID> {

    List<shoppingListEntity> findByCompanyId(UUID companyId);
}