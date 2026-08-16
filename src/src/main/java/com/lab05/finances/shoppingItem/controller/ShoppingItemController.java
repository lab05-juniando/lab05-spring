package com.lab05.finances.shoppingItem.controller;

import com.lab05.finances.shoppingItem.entity.shoppingItem;
import com.lab05.finances.shoppingItem.repository.ShoppingItemRepository;
import com.lab05.finances.shoppingItem.service.ShoppingItemCsvExportService;
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
@RequestMapping("/shopping-items")
public class ShoppingItemController {

    private final ShoppingItemRepository repository;
    private final ShoppingItemCsvExportService csvExportService;

    public ShoppingItemController(ShoppingItemRepository repository,
                                   ShoppingItemCsvExportService csvExportService) {
        this.repository = repository;
        this.csvExportService = csvExportService;
    }

    // GET /shopping-items/export                                  -> exporta tudo
    // GET /shopping-items/export?comprado=true                    -> só comprados
    // GET /shopping-items/export?categoria=Alimentação            -> só de uma categoria
    // GET /shopping-items/export?comprado=false&categoria=Limpeza -> combina os dois
    @GetMapping("/export")
    public ResponseEntity<byte[]> exportarCsv(
            @RequestParam(required = false) Boolean comprado,
            @RequestParam(required = false) String categoria
    ) throws IOException {

        List<shoppingItem> itens = repository.findByFiltros(comprado, categoria);
        byte[] csv = csvExportService.exportar(itens);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"shopping_items.csv\"")
                .contentType(MediaType.parseMediaType("text/csv; charset=UTF-8"))
                .body(csv);
    }
}
