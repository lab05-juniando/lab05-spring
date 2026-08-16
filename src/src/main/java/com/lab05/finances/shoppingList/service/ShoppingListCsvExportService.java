package com.lab05.finances.shoppingList.service;

import com.lab05.finances.shoppingList.entity.shoppingList;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class ShoppingListCsvExportService {

    public byte[] exportar(List<shoppingList> listas) throws IOException {
        StringWriter writer = new StringWriter();

        CSVFormat format = CSVFormat.DEFAULT.builder()
                .setDelimiter(';')
                .setHeader("ID", "Nome da Lista", "Ativa", "ID Empresa", "Total de Itens")
                .build();

        try (CSVPrinter printer = new CSVPrinter(writer, format)) {
            for (shoppingList list : listas) {
                printer.printRecord(
                        list.getId(),
                        list.getShoppingListName(),
                        Boolean.TRUE.equals(list.getShoppingListStatus()) ? "Sim" : "Não",
                        list.getCompanyId(),
                        list.getItems() != null ? list.getItems().size() : 0
                );
            }
        }

        // BOM UTF-8 para o Excel reconhecer acentos corretamente
        byte[] bom = new byte[]{(byte) 0xEF, (byte) 0xBB, (byte) 0xBF};
        byte[] csvBytes = writer.toString().getBytes(StandardCharsets.UTF_8);
        byte[] result = new byte[bom.length + csvBytes.length];
        System.arraycopy(bom, 0, result, 0, bom.length);
        System.arraycopy(csvBytes, 0, result, bom.length, csvBytes.length);

        return result;
    }
}
