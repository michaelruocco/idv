package uk.co.idv.domain.entities.verificationcontext;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.verificationcontext.VerificationSequence.MethodNotFoundInSequenceException;

import static org.assertj.core.api.Assertions.assertThat;

class MethodNotFoundInSequenceExceptionTest {

    private static final String METHOD_NAME = "method-name";
    private static final String SEQUENCE_NAME = "sequence-name";

    @Test
    void shouldReturnMessage() {
        final Throwable error = new MethodNotFoundInSequenceException(METHOD_NAME, SEQUENCE_NAME);

        final String expectedMessage = String.format("cannot find method %s in sequence %s sequence", METHOD_NAME, SEQUENCE_NAME);
        assertThat(error.getMessage()).isEqualTo(expectedMessage);
    }

    @Test
    void shouldReturnMethodName() {
        final MethodNotFoundInSequenceException error = new MethodNotFoundInSequenceException(METHOD_NAME, SEQUENCE_NAME);

        assertThat(error.getMethodName()).isEqualTo(METHOD_NAME);
    }

    @Test
    void shouldReturnSequenceName() {
        final MethodNotFoundInSequenceException error = new MethodNotFoundInSequenceException(METHOD_NAME, SEQUENCE_NAME);

        assertThat(error.getSequenceName()).isEqualTo(SEQUENCE_NAME);
    }

}
