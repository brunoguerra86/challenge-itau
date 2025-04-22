package com.brunoguerra.challenge_itau.service;

import com.brunoguerra.challenge_itau.model.Transacao;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransacaoService {

    private final List<Transacao> transacoes = new ArrayList<>();

    public void adicionarTransacao(Transacao transacao) {
        transacoes.add(transacao);
    }

    public void limparTransacoes() {
        transacoes.clear();
    }

    public Estatistica calcularEstatisticas() {
        OffsetDateTime agora = OffsetDateTime.now();
        List<Transacao> transacoesValidas = transacoes.stream()
                .filter(t -> t.dataHora().isAfter(agora.minusSeconds(60)))
                .toList();

        BigDecimal sum = transacoesValidas.stream()
                .map(Transacao::valor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal min = transacoesValidas.stream()
                .map(Transacao::valor)
                .min(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);

        BigDecimal max = transacoesValidas.stream()
                .map(Transacao::valor)
                .max(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);

        BigDecimal avg = transacoesValidas.isEmpty() ? BigDecimal.ZERO :
                sum.divide(BigDecimal.valueOf(transacoesValidas.size()), BigDecimal.ROUND_HALF_UP);

        return new Estatistica(transacoesValidas.size(), sum, avg, min, max);
    }

    public record Estatistica(long count, BigDecimal sum, BigDecimal avg, BigDecimal min, BigDecimal max) {
    }
}