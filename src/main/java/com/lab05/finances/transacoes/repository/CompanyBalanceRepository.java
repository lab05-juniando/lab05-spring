package com.lab05.finances.transacoes.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lab05.finances.transacoes.entity.CompanyBalance;

public interface CompanyBalanceRepository extends JpaRepository<CompanyBalance, UUID> {
}