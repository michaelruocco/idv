package uk.co.mruoc.idv.domain.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MethodNotSupportedExceptionTest {

    @Test
    void shouldReturnMessage() {
        final String methodName = "method-name";

        final Throwable error = new MethodNotSupportedException(methodName);

        assertThat(error.getMessage()).isEqualTo(methodName);
    }

}
