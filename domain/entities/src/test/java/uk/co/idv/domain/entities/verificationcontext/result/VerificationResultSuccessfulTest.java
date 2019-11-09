package uk.co.idv.domain.entities.verificationcontext.result;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResultSuccessful.VerificationResultSuccessfulBuilder;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class VerificationResultSuccessfulTest {

    private final VerificationResultSuccessfulBuilder builder = VerificationResultSuccessful.builder();

    @Test
    void shouldBeSuccessful() {
        final VerificationResult result = builder.build();

        assertThat(result.isSuccessful()).isTrue();
    }

    @Test
    void shouldReturnMethodName() {
        final String methodName = "method-name";

        final VerificationResult result = builder.methodName(methodName).build();

        assertThat(result.getMethodName()).isEqualTo(methodName);
    }

    @Test
    void shouldReturnVerificationId() {
        final UUID verificationId = UUID.randomUUID();

        final VerificationResult result = builder.verificationId(verificationId).build();

        assertThat(result.getVerificationId()).isEqualTo(verificationId);
    }

    @Test
    void shouldReturnTimestamp() {
        final Instant timestamp = Instant.now();

        final VerificationResult result = builder.timestamp(timestamp).build();

        assertThat(result.getTimestamp()).isEqualTo(timestamp);
    }

}
