package com.lab05.finances.transacoes.repository;

import com.lab05.finances.transacoes.entity.Transacao;
import com.lab05.finances.transacoes.entity.TipoTransacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

    @Query("SELECT t FROM Transacao t WHERE " +
           "(:tipo IS NULL OR t.tipo = :tipo) AND " +
           "(:dataInicio IS NULL OR t.data >= :dataInicio) AND " +
           "(:dataFim IS NULL OR t.data <= :dataFim)")
    List<Transacao> findByFiltros(@Param("tipo") TipoTransacao tipo,
                                   @Param("dataInicio") LocalDate dataInicio,
                                   @Param("dataFim") LocalDate dataFim);
}
