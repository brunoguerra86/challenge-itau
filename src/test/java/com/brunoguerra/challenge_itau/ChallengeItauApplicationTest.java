package com.brunoguerra.challenge_itau;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class ChallengeItauApplicationTest {

    // Application starts successfully with default configuration
    @Test
    public void test_application_starts_successfully_with_default_configuration() {
        // Arrange
        String[] args = new String[0];

        // Act & Assert
        assertDoesNotThrow(() -> ChallengeItauApplication.main(args));
    }
}
