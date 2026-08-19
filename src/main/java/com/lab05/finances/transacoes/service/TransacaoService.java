package com.lab05.finances.transacoes.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.lab05.finances.transacoes.dto.DashboardResponseDTO;
import com.lab05.finances.transacoes.dto.DashboardResponseDTO.CashFlowDTO;
import com.lab05.finances.transacoes.entity.Transacao;
import com.lab05.finances.transacoes.entity.TipoTransacao;
import com.lab05.finances.transacoes.repository.TransacaoRepository;

@Service
public class TransacaoService {

	@Autowired
	private TransacaoRepository repository;

	// Retorna lista com todos registros da tabela transacao
	public List<Transacao> findAll(){
		return repository.findAll();
	}

	public Transacao findById(Long id) {
		Optional<Transacao> obj = repository.findById(id);
		return obj.orElseThrow();
	}

	public Transacao insert(Transacao transacao) {
		return repository.save(transacao);
	}

	public void delete(Long id) {
		if (!repository.existsById(id)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Transação não encontrada");
		}
		repository.deleteById(id);
	}

	public Transacao update(Long id, Transacao transacao) {
		Transacao entity = repository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Transação não encontrada"));
		updateData(entity, transacao);
		return repository.save(entity);
	}

	private void updateData(Transacao entity, Transacao transacao) {
		entity.setDescription(transacao.getDescription());
		entity.setAmount(transacao.getAmount());
		entity.setDate(transacao.getDate());
		entity.setType(transacao.getType());
		entity.setNote(transacao.getNote());
	}

	public DashboardResponseDTO getDashboard(UUID companyId, LocalDate start, LocalDate end) {

		BigDecimal income = repository.sumByTypeAndPeriod(companyId, TipoTransacao.RECEITA, start, end);
		BigDecimal expenses = repository.sumByTypeAndPeriod(companyId, TipoTransacao.DESPESA, start, end);
		BigDecimal balance = income.subtract(expenses);

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

		return new DashboardResponseDTO(balance, income, expenses, forecast, cashFlow);
	}
}