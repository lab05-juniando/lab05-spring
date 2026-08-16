package com.lab05.finances.transacoes.service;

import com.lab05.finances.transacoes.entity.Transacao;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class TransacaoCsvExportService {

    private static final DateTimeFormatter DATA_FORMAT =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public byte[] exportar(List<Transacao> transacoes) throws IOException {
        StringWriter writer = new StringWriter();

        CSVFormat format = CSVFormat.DEFAULT.builder()
                .setDelimiter(';')
                .setHeader("ID", "Descrição", "Valor", "Data", "Tipo", "Observação")
                .build();

        try (CSVPrinter printer = new CSVPrinter(writer, format)) {
            for (Transacao t : transacoes) {
                printer.printRecord(
                        t.getId(),
                        t.getDescricao(),
                        t.getValor() != null
                                ? t.getValor().toString().replace(".", ",")
                                : "",
                        t.getData() != null ? t.getData().format(DATA_FORMAT) : "",
                        t.getTipo() != null ? t.getTipo().name() : "",
                        t.getObservacao() != null ? t.getObservacao() : ""
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
