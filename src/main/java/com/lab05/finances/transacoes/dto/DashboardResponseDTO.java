package com.lab05.finances.transacoes.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.lab05.finances.transacoes.entity.TipoTransacao;

public record DashboardResponseDTO(
        BigDecimal saldo,
        BigDecimal entradas,
        BigDecimal saidas,
        BigDecimal previsao,
        List<FluxoCaixaDTO> fluxoCaixa
) {

    public record FluxoCaixaDTO(
            LocalDate data,
            TipoTransacao tipo,
            BigDecimal total
    ) {
    }
}