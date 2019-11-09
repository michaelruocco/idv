package uk.co.idv.domain.entities.verificationcontext.result;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class VerificationResultFailedTest {

    private static final String METHOD_NAME = "method-name";
    private static final UUID VERIFICATION_ID = UUID.randomUUID();
    private static final Instant TIMESTAMP = Instant.now();

    private final VerificationResult result = new VerificationResultFailed(METHOD_NAME, VERIFICATION_ID, TIMESTAMP);

    @Test
    void shouldNotBeSuccessful() {
        assertThat(result.isSuccessful()).isFalse();
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
