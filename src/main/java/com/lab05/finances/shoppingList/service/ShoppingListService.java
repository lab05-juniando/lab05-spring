package com.lab05.finances.shoppingList.service;

import com.lab05.finances.shoppingList.dto.ShoppingListRequestDTO;
import com.lab05.finances.shoppingList.dto.ShoppingListResponseDTO;
import com.lab05.finances.shoppingList.entity.shoppingList;
import com.lab05.finances.shoppingList.repository.ShoppingListRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ShoppingListService {

    private final ShoppingListRepository shoppingListRepository;

    public ShoppingListService(ShoppingListRepository shoppingListRepository) {
        this.shoppingListRepository = shoppingListRepository;
    }

    @Transactional
    public ShoppingListResponseDTO create(ShoppingListRequestDTO dto) {
        shoppingList list = new shoppingList(
                dto.getShoppingListName(),
                dto.getShoppingListStatus() != null ? dto.getShoppingListStatus() : Boolean.TRUE,
                dto.getCompanyId()
        );

        shoppingList saved = shoppingListRepository.save(list);
        return toResponseDTO(saved);
    }

    @Transactional(readOnly = true)
    public List<ShoppingListResponseDTO> findAll() {
        return shoppingListRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ShoppingListResponseDTO> findAllByCompany(UUID companyId) {
        return shoppingListRepository.findByCompanyId(companyId)
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ShoppingListResponseDTO findById(UUID id) {
        return toResponseDTO(getListOrThrow(id));
    }

    @Transactional
    public ShoppingListResponseDTO update(UUID id, ShoppingListRequestDTO dto) {
        shoppingList list = getListOrThrow(id);

        list.setShoppingListName(dto.getShoppingListName());
        list.setShoppingListStatus(dto.getShoppingListStatus());
        list.setCompanyId(dto.getCompanyId());

        return toResponseDTO(shoppingListRepository.save(list));
    }

    @Transactional
    public void delete(UUID id) {
        shoppingList list = getListOrThrow(id);
        shoppingListRepository.delete(list); // remove os itens em cascata
    }

    private shoppingList getListOrThrow(UUID id) {
        return shoppingListRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Lista de compras não encontrada"));
    }

    private ShoppingListResponseDTO toResponseDTO(shoppingList list) {
        return new ShoppingListResponseDTO(
                list.getId(),
                list.getShoppingListName(),
                list.getShoppingListStatus(),
                list.getCompanyId(),
                list.getItems() != null ? list.getItems().size() : 0
        );
    }
}