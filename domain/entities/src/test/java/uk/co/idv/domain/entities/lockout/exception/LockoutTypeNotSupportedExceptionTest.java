package uk.co.idv.domain.entities.lockout.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LockoutTypeNotSupportedExceptionTest {

    @Test
    void shouldReturnMessage() {
        final String type = "lockout-type";

        final Throwable error = new LockoutTypeNotSupportedException(type);

        assertThat(error.getMessage()).isEqualTo(type);
    }

}
