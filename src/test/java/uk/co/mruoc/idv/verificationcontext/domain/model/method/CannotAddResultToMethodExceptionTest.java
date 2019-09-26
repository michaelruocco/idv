package uk.co.mruoc.idv.verificationcontext.domain.model.method;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.VerificationMethod.CannotAddResultToMethodException;

import static org.assertj.core.api.Assertions.assertThat;

class CannotAddResultToMethodExceptionTest {

    private static final String METHOD_NAME = "method-name";
    private static final String RESULT_METHOD_NAME = "result-method-name";

    @Test
    void shouldReturnMessage() {
        final Throwable error = new CannotAddResultToMethodException(RESULT_METHOD_NAME, METHOD_NAME);

        final String expectedMessage = String.format("cannot add result for method %s to method %s", RESULT_METHOD_NAME, METHOD_NAME);
        assertThat(error.getMessage()).isEqualTo(expectedMessage);
    }

    @Test
    void shouldReturnResultMethodName() {
        final CannotAddResultToMethodException error = new CannotAddResultToMethodException(RESULT_METHOD_NAME, METHOD_NAME);

        assertThat(error.getResultMethodName()).isEqualTo(RESULT_METHOD_NAME);
    }

    @Test
    void shouldReturnMethodName() {
        final CannotAddResultToMethodException error = new CannotAddResultToMethodException(RESULT_METHOD_NAME, METHOD_NAME);

        assertThat(error.getMethodName()).isEqualTo(METHOD_NAME);
    }

}
