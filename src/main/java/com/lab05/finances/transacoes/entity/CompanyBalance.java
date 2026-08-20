package com.lab05.finances.transacoes.entity;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "company_balances")
public class CompanyBalance {

    @Id
    @Column(name = "company_id")
    private UUID companyId;

    @Column(name = "current_balance", nullable = false, precision = 15, scale = 2)
    private BigDecimal currentBalance;

    public CompanyBalance() {
    }

    public CompanyBalance(UUID companyId, BigDecimal currentBalance) {
        this.companyId = companyId;
        this.currentBalance = currentBalance;
    }

    public UUID getCompanyId() {
        return companyId;
    }

    public void setCompanyId(UUID companyId) {
        this.companyId = companyId;
    }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(BigDecimal currentBalance) {
        this.currentBalance = currentBalance;
    }
}