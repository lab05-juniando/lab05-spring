package com.lab05.finances.shoppingList.service;

import com.lab05.finances.shoppingList.entity.shoppingListEntity;
import com.lab05.finances.shoppingList.repository.shoppingListRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class shoppingListService {

    private final shoppingListRepository shoppingListRepository;

    public shoppingListService(shoppingListRepository shoppingListRepository) {
        this.shoppingListRepository = shoppingListRepository;
    }

    @Transactional
    public shoppingListEntity create(UUID companyId) {
        if (shoppingListRepository.existsByCompanyId(companyId)) {
            throw new IllegalStateException(
                    "Já existe uma shopping list para a company com id: " + companyId);
        }

        shoppingListEntity entity = new shoppingListEntity(companyId);
        return shoppingListRepository.save(entity);
    }

    @Transactional(readOnly = true)
    public shoppingListEntity findById(UUID id) {
        return shoppingListRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shopping list não encontrada com id: " + id));
    }

    @Transactional(readOnly = true)
    public List<shoppingListEntity> findAll() {
        return shoppingListRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<shoppingListEntity> findByCompanyId(UUID companyId) {
        return shoppingListRepository.findByCompanyId(companyId);
    }

    @Transactional
    public shoppingListEntity update(UUID id, UUID companyId) {
        shoppingListEntity entity = shoppingListRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shopping list não encontrada com id: " + id));

        entity.setCompanyId(companyId);

        return shoppingListRepository.save(entity);
    }

    @Transactional
    public void delete(UUID id) {
        if (!shoppingListRepository.existsById(id)) {
            throw new RuntimeException("Shopping list não encontrada com id: " + id);
        }
        shoppingListRepository.deleteById(id);
    }
}