package com.lab05.finances.transacoes.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.lab05.finances.transacoes.repository.TransacaoRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RestController
@RequestMapping("/transacoes/filtro")
public class TransacaoFiltroController {
    @Autowired
    public TransacaoRepository transacaoRepository;

    @GetMapping("/")
    public String endereco() {
        return "Bem vindo ao endpoint de filtro de transações!";
    }

    @GetMapping("/listarTipoTransacao/{tipo}")
    public @ResponseBody String listarPorTipo(@RequestParam String tipo) {
        return transacaoRepository.findByTipo(tipo).toString();
    }

    @GetMapping("/listarCategoria/{descricao}")
    public @ResponseBody String listarPorDescricao(@RequestParam String categoria) {
        return transacaoRepository.findByDescricao(categoria).toString();
    }

    @GetMapping("/listarPeriodo/{data}")
    public @ResponseBody String listarPorData(@RequestParam LocalDate data) {
        return transacaoRepository.findByData(data).toString();
    }

}
