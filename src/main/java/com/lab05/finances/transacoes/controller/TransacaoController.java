package com.lab05.finances.transacoes.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lab05.finances.transacoes.dto.DashboardResponseDTO;
import com.lab05.finances.transacoes.entity.Transacao;
import com.lab05.finances.transacoes.service.TransacaoService;

@RestController
@RequestMapping("/transacoes")
public class TransacaoController {

    @Autowired
    private TransacaoService service;

    @GetMapping
    public List<Transacao> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Transacao findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public Transacao insert(@RequestBody Transacao transacao) {
        return service.insert(transacao);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public Transacao update(
            @PathVariable Long id,
            @RequestBody Transacao transacao) {

        return service.update(id, transacao);
    }

    @GetMapping("/dashboard")
    public DashboardResponseDTO dashboard(
            @RequestParam UUID companyId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {

        return service.getDashboard(companyId, start, end);
    }
}