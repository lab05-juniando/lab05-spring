package com.lab05.finances.transacoes.controller;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lab05.finances.transacoes.entity.CompanyBalance;
import com.lab05.finances.transacoes.service.CompanyBalanceService;

@RestController
@RequestMapping("/empresas/{companyId}/saldo")
public class CompanyBalanceController {

    @Autowired
    private CompanyBalanceService service;

    // Consulta o saldo atual persistido da empresa (cria zerado se ainda não existir).
    @GetMapping
    public CompanyBalance get(@PathVariable UUID companyId) {
        return service.getOrCreate(companyId);
    }

    // Define/ajusta manualmente o saldo inicial da empresa
    @PutMapping
    public CompanyBalance setInitialBalance(@PathVariable UUID companyId, @RequestBody SetBalanceRequest request) {
        return service.setBalance(companyId, request.currentBalance());
    }

    public record SetBalanceRequest(BigDecimal currentBalance) {
    }
}