package com.lab05.finances.transacoes.controller;

import com.lab05.finances.transacoes.entity.Transacao;
import com.lab05.finances.transacoes.entity.TipoTransacao;
import com.lab05.finances.transacoes.repository.TransacaoRepository;
import com.lab05.finances.transacoes.service.TransacaoCsvExportService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/transacoes")
public class TransacaoController {

    private final TransacaoRepository repository;
    private final TransacaoCsvExportService csvExportService;

    public TransacaoController(TransacaoRepository repository,
                                TransacaoCsvExportService csvExportService) {
        this.repository = repository;
        this.csvExportService = csvExportService;
    }

    // GET /transacoes/export                                        -> exporta tudo
    // GET /transacoes/export?tipo=DESPESA                           -> só despesas
    // GET /transacoes/export?dataInicio=2026-01-01&dataFim=2026-01-31 -> período
    @GetMapping("/export")
    public ResponseEntity<byte[]> exportarCsv(
            @RequestParam(required = false) TipoTransacao tipo,
            @RequestParam(required = false) LocalDate dataInicio,
            @RequestParam(required = false) LocalDate dataFim
    ) throws IOException {

        List<Transacao> transacoes = repository.findByFiltros(tipo, dataInicio, dataFim);
        byte[] csv = csvExportService.exportar(transacoes);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"transacoes.csv\"")
                .contentType(MediaType.parseMediaType("text/csv; charset=UTF-8"))
                .body(csv);
    }
}
