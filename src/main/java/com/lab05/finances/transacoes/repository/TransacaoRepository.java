package com.lab05.finances.transacoes.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.lab05.finances.transacoes.entity.Transacao;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
    // tipo de transação, categoria e periodo
    Transacao findByTipo(String tipo);

    Transacao findByDescricao(String descricao);

    Transacao findByData(LocalDate data);
}
