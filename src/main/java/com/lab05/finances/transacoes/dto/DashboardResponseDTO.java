package com.lab05.finances.transacoes.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.lab05.finances.transacoes.entity.TipoTransacao;

public record DashboardResponseDTO(
        BigDecimal balance,
        BigDecimal income,
        BigDecimal expenses,
        BigDecimal forecast,
        List<CashFlowDTO> cashFlow
) {

    public record CashFlowDTO(
            LocalDate date,
            TipoTransacao type,
            BigDecimal total
    ) {
    }
}