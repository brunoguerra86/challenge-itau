package com.brunoguerra.challenge_itau;

import com.brunoguerra.challenge_itau.controller.TransacaoController;
import com.brunoguerra.challenge_itau.exception.GlobalExceptionHandler;
import com.brunoguerra.challenge_itau.model.Transacao;
import com.brunoguerra.challenge_itau.service.TransacaoService;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TransacaoControllerTest {

    // Successfully add a valid transaction with proper value and date
    @Test
    public void test_add_valid_transaction() {
        // Arrange
        TransacaoService transacaoService = new TransacaoService();
        TransacaoController controller = new TransacaoController(transacaoService);
        Transacao transacao = new Transacao(
                new BigDecimal("100.00"),
                OffsetDateTime.now().minusSeconds(5)
        );

        // Act
        controller.adicionarTransacao(transacao);

        // Assert
        TransacaoService.Estatistica estatistica = controller.obterEstatisticas();
        assertEquals(1, estatistica.count());
        assertEquals(new BigDecimal("100.00"), estatistica.sum());
    }

    // Successfully clear all transactions
    @Test
    public void test_clear_all_transactions() {
        // Arrange
        TransacaoService transacaoService = new TransacaoService();
        TransacaoController controller = new TransacaoController(transacaoService);
        Transacao transacao = new Transacao(
                new BigDecimal("100.00"),
                OffsetDateTime.now().minusMinutes(5)
        );
        controller.adicionarTransacao(transacao);

        // Act
        controller.limparTransacoes();

        // Assert
        TransacaoService.Estatistica estatistica = controller.obterEstatisticas();
        assertEquals(0, estatistica.count());
        assertEquals(BigDecimal.ZERO, estatistica.sum());
    }

    // Handles a MethodArgumentNotValidException with a single field error
    @Test
    public void test_single_field_error() {
        // Arrange
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError = new FieldError("object", "name", "Name is required");

        when(ex.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getFieldErrors()).thenReturn(Collections.singletonList(fieldError));

        // Act
        ResponseEntity<Map<String, String>> response = handler.handleValidationExceptions(ex);

        // Assert
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals("Name is required", response.getBody().get("name"));
    }

}
