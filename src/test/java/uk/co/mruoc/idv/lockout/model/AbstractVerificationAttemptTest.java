package uk.co.mruoc.idv.lockout.model;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.idv.identity.domain.model.Alias;
import uk.co.mruoc.idv.identity.domain.model.FakeCreditCardNumber;
import uk.co.mruoc.idv.lockout.domain.AbstractVerificationAttempt;
import uk.co.mruoc.idv.lockout.domain.VerificationAttempt;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class AbstractVerificationAttemptTest {

    private static final UUID CONTEXT_ID = UUID.randomUUID();
    private static final String CHANNEL_ID = "channel-id";
    private static final String ACTIVITY_NAME = "activity-name";
    private static final Alias PROVIDED_ALIAS = new FakeCreditCardNumber();
    private static final UUID IDV_ID = UUID.randomUUID();
    private static final String METHOD_NAME = "method-name";
    private static final UUID VERIFICATION_ID = UUID.randomUUID();
    private static final Instant TIMESTAMP = Instant.now();
    private static final boolean SUCCESSFUL = true;

    private final VerificationAttempt result = new FakeVerificationAttempt(
            CONTEXT_ID,
            CHANNEL_ID,
            ACTIVITY_NAME,
            PROVIDED_ALIAS,
            IDV_ID,
            METHOD_NAME,
            VERIFICATION_ID,
            TIMESTAMP,
            SUCCESSFUL
    );

    @Test
    void shouldReturnContextId() {
        assertThat(result.getContextId()).isEqualTo(CONTEXT_ID);
    }

    @Test
    void shouldReturnChannelId() {
        assertThat(result.getChannelId()).isEqualTo(CHANNEL_ID);
    }

    @Test
    void shouldReturnActivityName() {
        assertThat(result.getActivityName()).isEqualTo(ACTIVITY_NAME);
    }

    @Test
    void shouldReturnProvidedAlias() {
        assertThat(result.getProvidedAlias()).isEqualTo(PROVIDED_ALIAS);
    }

    @Test
    void shouldReturnIdvId() {
        assertThat(result.getIdvId()).isEqualTo(IDV_ID);
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

    @Test
    void shouldReturnSuccessful() {
        assertThat(result.isSuccessful()).isEqualTo(SUCCESSFUL);
    }

    private static class FakeVerificationAttempt extends AbstractVerificationAttempt {

        private FakeVerificationAttempt(final UUID contextId,
                                        final String channelId,
                                        final String activityName,
                                        final Alias providedAlias,
                                        final UUID idvId,
                                        final String methodName,
                                        final UUID verificationId,
                                        final Instant timestamp,
                                        final boolean successful) {
            super(contextId,
                    channelId,
                    activityName,
                    providedAlias,
                    idvId,
                    methodName,
                    verificationId,
                    timestamp,
                    successful);
        }

    }

}
