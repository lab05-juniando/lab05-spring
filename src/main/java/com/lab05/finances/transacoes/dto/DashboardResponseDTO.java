package com.lab05.finances.transacoes.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.lab05.finances.transacoes.entity.TipoTransacao;

public record DashboardResponseDTO(
        // balance = saldo calculado apenas com as transações do período filtrado (start/end)
        BigDecimal balance,
        // currentBalance = saldo atual da empresa, independente do período
        BigDecimal currentBalance,
        BigDecimal saldoEntrada,
        BigDecimal saldoSaida,
        BigDecimal monthlyBalanceChangePercentage,
        BigDecimal income,
        BigDecimal expenses,
        BigDecimal forecast,
        List<CashFlowDTO> cashFlow,
        List<RecentTransactionDTO> recentTransactions
) {

    public record CashFlowDTO(
            LocalDate date,
            TipoTransacao type,
            BigDecimal total
    ) {
    }

    public record RecentTransactionDTO(
            Long id,
            UUID companyId,
            String description,
            BigDecimal amount,
            LocalDate date,
            TipoTransacao type,
            String note
    ) {
    }
}