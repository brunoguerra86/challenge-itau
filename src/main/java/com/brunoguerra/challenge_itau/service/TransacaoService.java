package com.brunoguerra.challenge_itau.service;

import com.brunoguerra.challenge_itau.model.Transacao;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransacaoService {

    private final List<Transacao> transacoes = new ArrayList<>();

    public void adicionarTransacao(Transacao transacao) {
        transacoes.add(transacao);
    }

}