package com.brunoguerra.challenge_itau.controller;

import com.brunoguerra.challenge_itau.model.Transacao;
import com.brunoguerra.challenge_itau.service.TransacaoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/transacao")
public class TransacaoController {

    private final TransacaoService transacaoService;

    public TransacaoController(TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void adicionarTransacao(@Valid @RequestBody Transacao transacao) {
        transacaoService.adicionarTransacao(transacao);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void limparTransacoes() {
        transacaoService.limparTransacoes();
    }

    @GetMapping("/estatistica")
    public TransacaoService.Estatistica obterEstatisticas() {
        return transacaoService.calcularEstatisticas();
    }

}