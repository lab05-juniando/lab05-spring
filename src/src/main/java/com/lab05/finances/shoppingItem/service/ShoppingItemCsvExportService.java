package com.lab05.finances.shoppingItem.service;

import com.lab05.finances.shoppingItem.entity.shoppingItem;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ShoppingItemCsvExportService {

    private static final DateTimeFormatter DATA_FORMAT =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public byte[] exportar(List<shoppingItem> itens) throws IOException {
        StringWriter writer = new StringWriter();

        CSVFormat format = CSVFormat.DEFAULT.builder()
                .setDelimiter(';')
                .setHeader("ID", "Lista", "Item", "Valor", "Descrição",
                           "Categoria", "Comprado", "Data da Compra")
                .build();

        try (CSVPrinter printer = new CSVPrinter(writer, format)) {
            for (shoppingItem item : itens) {
                printer.printRecord(
                        item.getId(),
                        item.getShoppingList() != null
                                ? item.getShoppingList().getShoppingListName()
                                : "",
                        item.getItemName(),
                        item.getItemValue() != null
                                ? item.getItemValue().toString().replace(".", ",")
                                : "",
                        item.getItemDescription() != null ? item.getItemDescription() : "",
                        item.getItemCategory() != null ? item.getItemCategory() : "",
                        Boolean.TRUE.equals(item.getItemStatus()) ? "Sim" : "Não",
                        item.getPurchaseDate() != null
                                ? item.getPurchaseDate().format(DATA_FORMAT)
                                : ""
                );
            }
        }

        byte[] bom = new byte[]{(byte) 0xEF, (byte) 0xBB, (byte) 0xBF};
        byte[] csvBytes = writer.toString().getBytes(StandardCharsets.UTF_8);
        byte[] result = new byte[bom.length + csvBytes.length];
        System.arraycopy(bom, 0, result, 0, bom.length);
        System.arraycopy(csvBytes, 0, result, bom.length, csvBytes.length);

        return result;
    }
}
