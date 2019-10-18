package uk.co.mruoc.idv.verificationcontext.domain.model;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.idv.verificationcontext.domain.model.VerificationSequences.NotNextMethodInSequenceException;

import static org.assertj.core.api.Assertions.assertThat;

class NotNextMethodInSequenceExceptionTest {

    @Test
    void shouldReturnMessage() {
        final String methodName = "method-name";

        final Throwable error = new NotNextMethodInSequenceException(methodName);

        assertThat(error.getMessage()).isEqualTo("method-name is not the next method in any sequences");
    }

}
