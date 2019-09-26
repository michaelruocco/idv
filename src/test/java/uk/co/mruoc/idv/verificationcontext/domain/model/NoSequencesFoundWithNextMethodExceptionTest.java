package uk.co.mruoc.idv.verificationcontext.domain.model;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.idv.verificationcontext.domain.model.VerificationSequences.NoSequencesFoundWithNextMethodException;

import static org.assertj.core.api.Assertions.assertThat;

class NoSequencesFoundWithNextMethodExceptionTest {

    @Test
    void shouldReturnMessage() {
        final String message = "method-name";

        final Throwable error = new NoSequencesFoundWithNextMethodException(message);

        assertThat(error.getMessage()).isEqualTo(message);
    }

}
