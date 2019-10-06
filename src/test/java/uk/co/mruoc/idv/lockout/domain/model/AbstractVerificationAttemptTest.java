package uk.co.mruoc.idv.lockout.domain.model;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.idv.identity.domain.model.Alias;
import uk.co.mruoc.idv.identity.domain.model.FakeCreditCardNumber;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class AbstractVerificationAttemptTest {

    private static final UUID CONTEXT_ID = UUID.fromString("8f0afda8-df73-4ea9-b7b2-2ee45e667227");
    private static final String CHANNEL_ID = "channel-id";
    private static final String ACTIVITY_NAME = "activity-name";
    private static final Alias PROVIDED_ALIAS = new FakeCreditCardNumber();
    private static final UUID IDV_ID_VALUE = UUID.fromString("f8c33391-7d70-4abe-bd8a-8e41e64f446c");
    private static final String METHOD_NAME = "method-name";
    private static final UUID VERIFICATION_ID = UUID.fromString("ca0b47c5-4bac-4122-ba82-8cbf2713922e");
    private static final Instant TIMESTAMP = Instant.parse("2019-09-27T09:40:29.982887Z");
    private static final boolean SUCCESSFUL = true;

    private final VerificationAttempt attempt = new FakeVerificationAttempt(
            CONTEXT_ID,
            CHANNEL_ID,
            ACTIVITY_NAME,
            PROVIDED_ALIAS,
            IDV_ID_VALUE,
            METHOD_NAME,
            VERIFICATION_ID,
            TIMESTAMP,
            SUCCESSFUL
    );

    @Test
    void shouldReturnContextId() {
        assertThat(attempt.getContextId()).isEqualTo(CONTEXT_ID);
    }

    @Test
    void shouldReturnChannelId() {
        assertThat(attempt.getChannelId()).isEqualTo(CHANNEL_ID);
    }

    @Test
    void shouldReturnActivityName() {
        assertThat(attempt.getActivityName()).isEqualTo(ACTIVITY_NAME);
    }

    @Test
    void shouldReturnProvidedAlias() {
        assertThat(attempt.getAlias()).isEqualTo(PROVIDED_ALIAS);
    }

    @Test
    void shouldReturnIdvId() {
        assertThat(attempt.getIdvIdValue()).isEqualTo(IDV_ID_VALUE);
    }

    @Test
    void shouldReturnMethodName() {
        assertThat(attempt.getMethodName()).isEqualTo(METHOD_NAME);
    }

    @Test
    void shouldReturnVerificationId() {
        assertThat(attempt.getVerificationId()).isEqualTo(VERIFICATION_ID);
    }

    @Test
    void shouldReturnTimestamp() {
        assertThat(attempt.getTimestamp()).isEqualTo(TIMESTAMP);
    }

    @Test
    void shouldReturnSuccessful() {
        assertThat(attempt.isSuccessful()).isEqualTo(SUCCESSFUL);
    }

    @Test
    void shouldReturnAliasType() {
        assertThat(attempt.getAliasType()).isEqualTo(PROVIDED_ALIAS.getType());
    }

    @Test
    void shouldPrintDetails() {
        final String details = attempt.toString();

        assertThat(details).isEqualTo("AbstractVerificationAttempt(" +
                "contextId=8f0afda8-df73-4ea9-b7b2-2ee45e667227, " +
                "channelId=channel-id, " +
                "activityName=activity-name, " +
                "alias=CardNumber(type=credit-card-number, value=4929001111111111), " +
                "idvIdValue=f8c33391-7d70-4abe-bd8a-8e41e64f446c, " +
                "methodName=method-name, " +
                "verificationId=ca0b47c5-4bac-4122-ba82-8cbf2713922e, " +
                "timestamp=2019-09-27T09:40:29.982887Z, " +
                "successful=true)");
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
