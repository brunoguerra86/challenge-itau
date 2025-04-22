package com.brunoguerra.challenge_itau.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record Transacao(
        @NotNull(message = "Campo obrigatorio")
        @DecimalMin(value = "0.01", message = "Valor deve ser maior que zero")
        BigDecimal valor,

        @NotNull(message = "Campo obrigatorio")
        @PastOrPresent(message = "Data futura não é permitida")
        OffsetDateTime dataHora) {
}
