package uk.co.mruoc.verificationcontext.domain.result;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class AbstractVerificationResultTest {

    private static final String METHOD_NAME = "method-name";
    private static final UUID VERIFICATION_ID = UUID.randomUUID();
    private static final Instant TIMESTAMP = Instant.now();
    private static final boolean SUCCESSFUL = true;

    private final VerificationResult result = new FakeVerificationResult(
            METHOD_NAME,
            VERIFICATION_ID,
            TIMESTAMP,
            SUCCESSFUL
    );

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

    @Test
    void shouldReturnSuccessful() {
        assertThat(result.isSuccessful()).isEqualTo(SUCCESSFUL);
    }

    @Test
    void shouldReturnHasMethodNameTrueIfNameMatches() {
        final boolean hasMethodName = result.hasMethodName(METHOD_NAME);

        assertThat(hasMethodName).isTrue();
    }

    @Test
    void shouldReturnHasMethodNameFalseIfNameDoesNotMatch() {
        final boolean hasMethodName = result.hasMethodName("other-name");

        assertThat(hasMethodName).isFalse();
    }

    private static class FakeVerificationResult extends AbstractVerificationResult {

        private FakeVerificationResult(final String name,
                                      final UUID verificationId,
                                      final Instant timestamp,
                                      final boolean successful) {
            super(name, verificationId, timestamp, successful);
        }

    }

}