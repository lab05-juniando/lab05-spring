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
import com.lab05.finances.transacoes.dto.DashboardResponseDTO.FluxoCaixaDTO;
import com.lab05.finances.transacoes.entity.Transacao;
import com.lab05.finances.transacoes.entity.TipoTransacao;
import com.lab05.finances.transacoes.repository.TransacaoRepository;

@Service
public class TransacaoService {

	@Autowired
	private TransacaoRepository repository;

	//Retorna lista com todos registros da tabela transacao
	public List<Transacao> findAll(){
		return repository.findAll();
	}

	public Transacao findById(Long id) {// repository.findById() retorna um Optional, que pode conter ou não um User
		Optional<Transacao> obj = repository.findById(id);
		return obj.orElseThrow();
	}

	public Transacao insert(Transacao obj) {
		return repository.save(obj);
	}

	public void delete(Long id) {
		if (!repository.existsById(id)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Transação não encontrada");
		}
		repository.deleteById(id);
	}

	public Transacao update(Long id, Transacao obj) {
		Transacao entity = repository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Transação não encontrada"));
		updateData(entity, obj);
		return repository.save(entity);
	}

	private void updateData(Transacao entity, Transacao obj) {
		entity.setDescricao(obj.getDescricao());
		entity.setValor(obj.getValor());
		entity.setData(obj.getData());
		entity.setTipo(obj.getTipo());
		entity.setObservacao(obj.getObservacao());
	}

	public DashboardResponseDTO getDashboard(UUID companyId, LocalDate inicio, LocalDate fim) {

		BigDecimal entradas = repository.sumPorTipoEPeriodo(companyId, TipoTransacao.RECEITA, inicio, fim);
		BigDecimal saidas = repository.sumPorTipoEPeriodo(companyId, TipoTransacao.DESPESA, inicio, fim);
		BigDecimal saldo = entradas.subtract(saidas);

		// TODO: previsao ainda é um placeholder — a definir regra de cálculo real
		BigDecimal previsao = saldo;

		List<Object[]> agrupado = repository.sumAgrupadoPorDiaETipo(companyId, inicio, fim);

		List<FluxoCaixaDTO> fluxoCaixa = new ArrayList<>();
		for (Object[] linha : agrupado) {
			LocalDate data = (LocalDate) linha[0];
			TipoTransacao tipo = (TipoTransacao) linha[1];
			BigDecimal total = (BigDecimal) linha[2];
			fluxoCaixa.add(new FluxoCaixaDTO(data, tipo, total));
		}

		return new DashboardResponseDTO(saldo, entradas, saidas, previsao, fluxoCaixa);
	}
}