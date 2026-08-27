package com.lab05.finances.transacoes.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lab05.finances.exceptions.ResourceNotFoundException;
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
		return repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Transacao", id));
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
				.orElseThrow(() -> new ResourceNotFoundException("Transacao", id));

		repository.deleteById(id);
		companyBalanceService.reverseTransaction(entity.getCompanyId(), entity.getAmount(), entity.getType());
	}

	@Transactional
	public Transacao update(Long id, Transacao transacao) {
		Transacao entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Transacao", id));

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
		BigDecimal saldoEntrada = saldoEntrada(companyId, end);
		BigDecimal saldoSaida = saldoSaida(companyId, end);
		BigDecimal monthlyBalanceChangePercentage = getMonthlyBalanceChangePercentage(companyId, end);

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

		return new DashboardResponseDTO(balance, currentBalance, saldoEntrada, saldoSaida, monthlyBalanceChangePercentage, income, expenses,
				forecast, cashFlow, recentTransactions);
	}

	public BigDecimal saldoEntrada(UUID companyId, LocalDate referenceDate) {
		LocalDate currentMonthStart = referenceDate.withDayOfMonth(1);
		BigDecimal total = BigDecimal.ZERO;

		for (int monthOffset = 0; monthOffset < 3; monthOffset++) {
			LocalDate monthStart = currentMonthStart.minusMonths(monthOffset);
			LocalDate monthEnd = monthStart.withDayOfMonth(monthStart.lengthOfMonth());
			total = total.add(repository.sumByTypeAndPeriod(companyId, TipoTransacao.RECEITA, monthStart, monthEnd));
		}

		return total.divide(BigDecimal.valueOf(3), 2, RoundingMode.HALF_UP);
	}

	public BigDecimal saldoSaida(UUID companyId, LocalDate referenceDate) {
		LocalDate monthStart = referenceDate.withDayOfMonth(1);
		LocalDate monthEnd = monthStart.withDayOfMonth(monthStart.lengthOfMonth());
		BigDecimal income = repository.sumByTypeAndPeriod(companyId, TipoTransacao.RECEITA, monthStart, monthEnd);
		BigDecimal expenses = repository.sumByTypeAndPeriod(companyId, TipoTransacao.DESPESA, monthStart, monthEnd);

		if (income.compareTo(BigDecimal.ZERO) == 0) {
			return null;
		}

		return expenses.divide(income, 4, RoundingMode.HALF_UP)
				.multiply(BigDecimal.valueOf(100))
				.setScale(2, RoundingMode.HALF_UP);
	}

	//Saldo
	public BigDecimal getMonthlyBalanceChangePercentage(UUID companyId, LocalDate referenceDate) {
		LocalDate currentMonthStart = referenceDate.withDayOfMonth(1);
		LocalDate currentMonthEnd = referenceDate.withDayOfMonth(referenceDate.lengthOfMonth());
		LocalDate previousMonthStart = currentMonthStart.minusMonths(1);
		LocalDate previousMonthEnd = currentMonthStart.minusDays(1);

		BigDecimal currentMonthBalance = repository.sumBalanceByPeriod(companyId, currentMonthStart, currentMonthEnd);
		BigDecimal previousMonthBalance = repository.sumBalanceByPeriod(companyId, previousMonthStart, previousMonthEnd);

		if (previousMonthBalance.compareTo(BigDecimal.ZERO) == 0) {
			return null;
		}

		return currentMonthBalance.subtract(previousMonthBalance)
				.divide(previousMonthBalance, 4, RoundingMode.HALF_UP)
				.multiply(BigDecimal.valueOf(100))
				.setScale(2, RoundingMode.HALF_UP);
	}
}