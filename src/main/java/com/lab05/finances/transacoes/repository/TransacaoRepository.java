package com.lab05.finances.transacoes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lab05.finances.transacoes.entity.Transacao;

public interface TransacaoRepository extends JpaRepository <Transacao, Long>{

}
