package com.lab05.finances.transacoes.dto;

import com.lab05.finances.transacoes.entity.Transacao;
import com.lab05.finances.transacoes.entity.TipoTransacao;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TransacaoExportDTO {

    private Long id;
    private String descricao;
    private BigDecimal valor;
    private LocalDate data;
    private TipoTransacao tipo;
    private String observacao;

    public TransacaoExportDTO(Transacao transacao) {
        this.id = transacao.getId();
        this.descricao = transacao.getDescricao();
        this.valor = transacao.getValor();
        this.data = transacao.getData();
        this.tipo = transacao.getTipo();
        this.observacao = transacao.getObservacao();
    }

    public Long getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public LocalDate getData() {
        return data;
    }

    public TipoTransacao getTipo() {
        return tipo;
    }

    public String getObservacao() {
        return observacao;
    }
}
