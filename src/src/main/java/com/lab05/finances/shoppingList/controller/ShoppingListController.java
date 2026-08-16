package com.lab05.finances.shoppingList.controller;

import com.lab05.finances.shoppingList.entity.shoppingList;
import com.lab05.finances.shoppingList.repository.ShoppingListRepository;
import com.lab05.finances.shoppingList.service.ShoppingListCsvExportService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/shopping-lists")
public class ShoppingListController {

    private final ShoppingListRepository repository;
    private final ShoppingListCsvExportService csvExportService;

    public ShoppingListController(ShoppingListRepository repository,
                                   ShoppingListCsvExportService csvExportService) {
        this.repository = repository;
        this.csvExportService = csvExportService;
    }

    // GET /shopping-lists/export             -> exporta tudo
    // GET /shopping-lists/export?ativa=true  -> só listas ativas
    @GetMapping("/export")
    public ResponseEntity<byte[]> exportarCsv(
            @RequestParam(required = false) Boolean ativa
    ) throws IOException {

        List<shoppingList> listas = repository.findByFiltros(ativa);
        byte[] csv = csvExportService.exportar(listas);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"shopping_lists.csv\"")
                .contentType(MediaType.parseMediaType("text/csv; charset=UTF-8"))
                .body(csv);
    }
}
