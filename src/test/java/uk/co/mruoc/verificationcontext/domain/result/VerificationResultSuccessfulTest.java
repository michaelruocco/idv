package uk.co.mruoc.verificationcontext.domain.result;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.verificationcontext.domain.result.VerificationResult;
import uk.co.mruoc.verificationcontext.domain.result.VerificationResultSuccessful;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class VerificationResultSuccessfulTest {

    private static final String METHOD_NAME = "method-name";
    private static final UUID VERIFICATION_ID = UUID.randomUUID();
    private static final Instant TIMESTAMP = Instant.now();

    private final VerificationResult result = new VerificationResultSuccessful(METHOD_NAME, VERIFICATION_ID, TIMESTAMP);

    @Test
    void shouldBeSuccessful() {
        assertThat(result.isSuccessful()).isTrue();
    }

    @Test
    void shouldReturnMethodName() {
        assertThat(result.getMethodName()).isEqualTo(METHOD_NAME);
    }

    @Test
    void shouldReturnVerificationId() {
        assertThat(result.getVerificationId()).isEqualTo(VERIFICATION_ID);
    }

    @Test
    void shouldReturnTimestamp() {
        assertThat(result.getTimestamp()).isEqualTo(TIMESTAMP);
    }

}