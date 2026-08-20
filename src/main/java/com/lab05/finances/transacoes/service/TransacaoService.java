package com.lab05.finances.transacoes.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.lab05.finances.transacoes.dto.DashboardResponseDTO;
import com.lab05.finances.transacoes.dto.DashboardResponseDTO.CashFlowDTO;
import com.lab05.finances.transacoes.dto.DashboardResponseDTO.RecentTransactionDTO;
import com.lab05.finances.transacoes.entity.Transacao;
import com.lab05.finances.transacoes.entity.TipoTransacao;
import com.lab05.finances.transacoes.repository.TransacaoRepository;

@Service
public class TransacaoService {

	private static final int DEFAULT_RECENT_LIMIT = 5;
	private static final int MAX_RECENT_LIMIT = 50;

	@Autowired
	private TransacaoRepository repository;

	@Autowired
	private CompanyBalanceService companyBalanceService;

	// Retorna lista com todos registros da tabela transacao
	public List<Transacao> findAll(){
		return repository.findAll();
	}

	public Transacao findById(Long id) {
		Optional<Transacao> obj = repository.findById(id);
		return obj.orElseThrow();
	}

	@Transactional
	public Transacao insert(Transacao transacao) {
		Transacao saved = repository.save(transacao);
		companyBalanceService.applyTransaction(saved.getCompanyId(), saved.getAmount(), saved.getType());
		return saved;
	}

	@Transactional
	public void delete(Long id) {
		Transacao entity = repository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Transação não encontrada"));

		repository.deleteById(id);
		companyBalanceService.reverseTransaction(entity.getCompanyId(), entity.getAmount(), entity.getType());
	}

	@Transactional
	public Transacao update(Long id, Transacao transacao) {
		Transacao entity = repository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Transação não encontrada"));

		UUID oldCompanyId = entity.getCompanyId();
		BigDecimal oldAmount = entity.getAmount();
		TipoTransacao oldType = entity.getType();

		updateData(entity, transacao);
		Transacao updated = repository.save(entity);

		companyBalanceService.reverseTransaction(oldCompanyId, oldAmount, oldType);
		companyBalanceService.applyTransaction(updated.getCompanyId(), updated.getAmount(), updated.getType());

		return updated;
	}

	private void updateData(Transacao entity, Transacao transacao) {
		entity.setDescription(transacao.getDescription());
		entity.setAmount(transacao.getAmount());
		entity.setDate(transacao.getDate());
		entity.setType(transacao.getType());
		entity.setNote(transacao.getNote());
	}

	// Busca as N transações mais recentes de uma empresa (mais novas primeiro).
	public List<Transacao> findRecent(UUID companyId, int limit) {
		int safeLimit = normalizeLimit(limit);
		Pageable pageable = PageRequest.of(0, safeLimit);
		return repository.findByCompanyIdOrderByDateDescIdDesc(companyId, pageable);
	}

	private int normalizeLimit(int limit) {
		if (limit <= 0) {
			return DEFAULT_RECENT_LIMIT;
		}
		return Math.min(limit, MAX_RECENT_LIMIT);
	}

	public DashboardResponseDTO getDashboard(UUID companyId, LocalDate start, LocalDate end) {
		return getDashboard(companyId, start, end, DEFAULT_RECENT_LIMIT);
	}

	public DashboardResponseDTO getDashboard(UUID companyId, LocalDate start, LocalDate end, int recentLimit) {

		// balance = saldo do PERÍODO filtrado (income - expenses no intervalo start/end)
		BigDecimal income = repository.sumByTypeAndPeriod(companyId, TipoTransacao.RECEITA, start, end);
		BigDecimal expenses = repository.sumByTypeAndPeriod(companyId, TipoTransacao.DESPESA, start, end);
		BigDecimal balance = income.subtract(expenses);

		// currentBalance = saldo atual da empresa, independente do período filtrado
		BigDecimal currentBalance = companyBalanceService.getCurrentBalance(companyId);

		// TODO: forecast ainda é um placeholder — a definir regra de cálculo real
		BigDecimal forecast = balance;

		List<Object[]> grouped = repository.sumGroupedByDayAndType(companyId, start, end);

		List<CashFlowDTO> cashFlow = new ArrayList<>();
		for (Object[] row : grouped) {
			LocalDate date = (LocalDate) row[0];
			TipoTransacao type = (TipoTransacao) row[1];
			BigDecimal total = (BigDecimal) row[2];
			cashFlow.add(new CashFlowDTO(date, type, total));
		}

		// Reutiliza o serviço de transações existente, apenas limitando a quantidade retornada
		List<Transacao> recent = findRecent(companyId, recentLimit);
		List<RecentTransactionDTO> recentTransactions = new ArrayList<>();
		for (Transacao t : recent) {
			recentTransactions.add(new RecentTransactionDTO(
					t.getId(),
					t.getCompanyId(),
					t.getDescription(),
					t.getAmount(),
					t.getDate(),
					t.getType(),
					t.getNote()
			));
		}

		return new DashboardResponseDTO(balance, currentBalance, income, expenses, forecast, cashFlow, recentTransactions);
	}
}