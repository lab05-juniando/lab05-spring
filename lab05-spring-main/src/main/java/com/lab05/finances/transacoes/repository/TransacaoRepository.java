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
        SELECT COALESCE(SUM(t.amount), 0)
        FROM Transacao t
        WHERE t.companyId = :companyId
          AND t.type = :type
          AND t.date BETWEEN :start AND :end
        """)
    BigDecimal sumByTypeAndPeriod(@Param("companyId") UUID companyId,
                                  @Param("type") TipoTransacao type,
                                  @Param("start") LocalDate start,
                                  @Param("end") LocalDate end);

    @Query("""
        SELECT t.date as date, t.type as type, SUM(t.amount) as total
        FROM Transacao t
        WHERE t.companyId = :companyId
          AND t.date BETWEEN :start AND :end
        GROUP BY t.date, t.type
        ORDER BY t.date
        """)
    List<Object[]> sumGroupedByDayAndType(@Param("companyId") UUID companyId,
                                          @Param("start") LocalDate start,
                                          @Param("end") LocalDate end);
}