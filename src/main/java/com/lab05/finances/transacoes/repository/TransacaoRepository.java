package com.lab05.finances.transacoes.repository;

import com.lab05.finances.transacoes.entity.Transacao;
import com.lab05.finances.transacoes.entity.TipoTransacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

    @Query("""
        SELECT COALESCE(SUM(t.valor), 0)
        FROM Transacao t
        WHERE t.companyId = :companyId
          AND t.tipo = :tipo
          AND t.data BETWEEN :inicio AND :fim
        """)
    BigDecimal sumPorTipoEPeriodo(@Param("companyId") UUID companyId,
                                  @Param("tipo") TipoTransacao tipo,
                                  @Param("inicio") LocalDate inicio,
                                  @Param("fim") LocalDate fim);

    @Query("""
        SELECT t.data as data, t.tipo as tipo, SUM(t.valor) as total
        FROM Transacao t
        WHERE t.companyId = :companyId
          AND t.data BETWEEN :inicio AND :fim
        GROUP BY t.data, t.tipo
        ORDER BY t.data
        """)
    List<Object[]> sumAgrupadoPorDiaETipo(@Param("companyId") UUID companyId,
                                          @Param("inicio") LocalDate inicio,
                                          @Param("fim") LocalDate fim);
}