package com.lab05.finances.transacoes.service;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lab05.finances.transacoes.entity.CompanyBalance;
import com.lab05.finances.transacoes.entity.TipoTransacao;
import com.lab05.finances.transacoes.repository.CompanyBalanceRepository;

@Service
public class CompanyBalanceService {

    @Autowired
    private CompanyBalanceRepository repository;

    // Retorna o saldo da empresa, criando o registro zerado se ainda não existir
    @Transactional
    public CompanyBalance getOrCreate(UUID companyId) {
        return repository.findById(companyId)
                .orElseGet(() -> repository.save(new CompanyBalance(companyId, BigDecimal.ZERO)));
    }

    public BigDecimal getCurrentBalance(UUID companyId) {
        return getOrCreate(companyId).getCurrentBalance();
    }

    // Define manualmente o saldo da empresa
    @Transactional
    public CompanyBalance setBalance(UUID companyId, BigDecimal newBalance) {
        CompanyBalance balance = getOrCreate(companyId);
        balance.setCurrentBalance(newBalance);
        return repository.save(balance);
    }

    // Aplica o efeito de uma transação no saldo: soma se RECEITA, subtrai se DESPESA.
    @Transactional
    public void applyTransaction(UUID companyId, BigDecimal amount, TipoTransacao type) {
        CompanyBalance balance = getOrCreate(companyId);
        balance.setCurrentBalance(balance.getCurrentBalance().add(resolveDelta(amount, type)));
        repository.save(balance);
    }

    @Transactional
    public void reverseTransaction(UUID companyId, BigDecimal amount, TipoTransacao type) {
        CompanyBalance balance = getOrCreate(companyId);
        balance.setCurrentBalance(balance.getCurrentBalance().subtract(resolveDelta(amount, type)));
        repository.save(balance);
    }

    private BigDecimal resolveDelta(BigDecimal amount, TipoTransacao type) {
        return type == TipoTransacao.RECEITA ? amount : amount.negate();
    }
}