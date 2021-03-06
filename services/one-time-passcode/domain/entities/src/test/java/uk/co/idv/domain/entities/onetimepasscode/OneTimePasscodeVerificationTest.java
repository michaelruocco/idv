package uk.co.idv.domain.entities.onetimepasscode;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.VerificationStatus;
import uk.co.idv.domain.entities.onetimepasscode.exception.NoDeliveriesRemainingException;
import uk.co.idv.domain.entities.onetimepasscode.exception.VerificationAlreadyCompleteException;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class OneTimePasscodeVerificationTest {

    @Test
    void shouldReturnId() {
        final UUID id = UUID.randomUUID();

        final OneTimePasscodeVerification verification = OneTimePasscodeVerification.builder()
                .id(id)
                .build();

        assertThat(verification.getId()).isEqualTo(id);
    }

    @Test
    void shouldReturnContextId() {
        final UUID contextId = UUID.randomUUID();

        final OneTimePasscodeVerification verification = OneTimePasscodeVerification.builder()
                .contextId(contextId)
                .build();

        assertThat(verification.getContextId()).isEqualTo(contextId);
    }

    @Test
    void shouldReturnCreated() {
        final Instant created = Instant.now();

        final OneTimePasscodeVerification verification = OneTimePasscodeVerification.builder()
                .created(created)
                .build();

        assertThat(verification.getCreated()).isEqualTo(created);
    }

    @Test
    void shouldReturnExpiry() {
        final Instant expiry = Instant.now();

        final OneTimePasscodeVerification verification = OneTimePasscodeVerification.builder()
                .expiry(expiry)
                .build();

        assertThat(verification.getExpiry()).isEqualTo(expiry);
    }

    @Test
    void shouldReturnMaxDeliveries() {
        final int maxDeliveries = 3;

        final OneTimePasscodeVerification verification = OneTimePasscodeVerification.builder()
                .maxDeliveries(maxDeliveries)
                .build();

        assertThat(verification.getMaxDeliveries()).isEqualTo(maxDeliveries);
    }

    @Test
    void shouldReturnMaxAttempts() {
        final int maxAttempts = 3;

        final OneTimePasscodeVerification verification = OneTimePasscodeVerification.builder()
                .maxAttempts(maxAttempts)
                .build();

        assertThat(verification.getMaxAttempts()).isEqualTo(maxAttempts);
    }

    @Test
    void shouldReturnPendingStatusByDefault() {
        final OneTimePasscodeVerification verification = OneTimePasscodeVerification.builder()
                .build();

        assertThat(verification.getStatus()).isEqualTo(VerificationStatus.PENDING);
    }

    @Test
    void shouldReturnEmptyDeliveriesByDefault() {
        final OneTimePasscodeVerification verification = OneTimePasscodeVerification.builder()
                .build();

        assertThat(verification.getDeliveries()).isEmpty();
    }

    @Test
    void shouldReturnEmptyAttemptsByDefault() {
        final OneTimePasscodeVerification verification = OneTimePasscodeVerification.builder()
                .build();

        assertThat(verification.getAttempts()).isEmpty();
    }

    @Test
    void shouldReturnNotBeCompleteByDefault() {
        final OneTimePasscodeVerification verification = OneTimePasscodeVerification.builder()
                .build();

        assertThat(verification.isComplete()).isFalse();
    }

    @Test
    void shouldReturnHasExpired() {
        final Instant expiry = Instant.now();

        final OneTimePasscodeVerification verification = OneTimePasscodeVerification.builder()
                .expiry(expiry)
                .build();

        assertThat(verification.hasExpired(expiry.minusMillis(1))).isFalse();
        assertThat(verification.hasExpired(expiry)).isFalse();
        assertThat(verification.hasExpired(expiry.plusMillis(1))).isTrue();
    }

    @Test
    void shouldThrowExceptionIfDeliveryIsRecordedWhenVerificationIsComplete() {
        final OneTimePasscodeVerification verification = OneTimePasscodeVerification.builder()
                .completed(Instant.now())
                .build();
        final OneTimePasscodeDelivery delivery = OneTimePasscodeDeliveryMother.smsDelivery();

        final Throwable error = catchThrowable(() -> verification.record(delivery));

        assertThat(error).isInstanceOf(VerificationAlreadyCompleteException.class);
    }

    @Test
    void shouldThrowExceptionIfDeliveryIsRecordedWhenNoDeliveriesAreRemaining() {
        final OneTimePasscodeVerification verification = OneTimePasscodeVerification.builder()
                .maxDeliveries(1)
                .build();
        final OneTimePasscodeDelivery delivery = OneTimePasscodeDeliveryMother.smsDelivery();
        verification.record(delivery);

        final Throwable error = catchThrowable(() -> verification.record(delivery));

        assertThat(error).isInstanceOf(NoDeliveriesRemainingException.class);
    }

    @Test
    void shouldDecrementDeliveriesRemainingAfterDeliveryIsRecorded() {
        final int maxDeliveries = 1;
        final OneTimePasscodeVerification verification = OneTimePasscodeVerification.builder()
                .maxDeliveries(maxDeliveries)
                .build();
        final OneTimePasscodeDelivery delivery = OneTimePasscodeDeliveryMother.smsDelivery();

        assertThat(verification.getDeliveriesRemaining()).isEqualTo(maxDeliveries);
        verification.record(delivery);
        assertThat(verification.getDeliveriesRemaining()).isEqualTo(maxDeliveries - 1);
    }

    @Test
    void shouldReturnRecordedDeliveries() {
        final OneTimePasscodeVerification verification = OneTimePasscodeVerification.builder()
                .maxDeliveries(1)
                .build();
        final OneTimePasscodeDelivery delivery = OneTimePasscodeDeliveryMother.smsDelivery();

        verification.record(delivery);

        assertThat(verification.getDeliveries()).containsExactly(delivery);
    }

    @Test
    void shouldThrowExceptionIfAttemptIsRecordedWhenVerificationIsComplete() {
        final OneTimePasscodeVerification verification = OneTimePasscodeVerification.builder()
                .completed(Instant.now())
                .build();
        final OneTimePasscodeVerificationAttempt attempt = OneTimePasscodeVerificationAttemptMother.attempt();

        final Throwable error = catchThrowable(() -> verification.verify(attempt));

        assertThat(error).isInstanceOf(VerificationAlreadyCompleteException.class);
    }

    @Test
    void shouldDecrementAttemptsRemainingAfterAttemptVerified() {
        final int maxAttempts = 1;
        final OneTimePasscodeVerification verification = OneTimePasscodeVerification.builder()
                .maxAttempts(maxAttempts)
                .build();
        final OneTimePasscodeVerificationAttempt attempt = OneTimePasscodeVerificationAttemptMother.attempt();

        assertThat(verification.getAttemptsRemaining()).isEqualTo(maxAttempts);
        verification.verify(attempt);
        assertThat(verification.getAttemptsRemaining()).isEqualTo(maxAttempts - 1);
    }

    @Test
    void shouldReturnVerifiedAttempts() {
        final OneTimePasscodeVerification verification = OneTimePasscodeVerification.builder()
                .maxAttempts(1)
                .build();
        final OneTimePasscodeVerificationAttempt attempt = OneTimePasscodeVerificationAttemptMother.attempt();

        verification.verify(attempt);

        assertThat(verification.getAttempts()).containsExactly(attempt);
    }

    @Test
    void shouldCompleteVerificationIfAttemptIsNotSuccessfulAndNoAttemptsAreRemaining() {
        final OneTimePasscodeVerification verification = OneTimePasscodeVerification.builder()
                .maxAttempts(1)
                .deliveries(OneTimePasscodeDeliveryMother.oneSmsDelivery("111111"))
                .build();
        final OneTimePasscodeVerificationAttempt attempt = OneTimePasscodeVerificationAttemptMother.builder()
                .passcode("222222")
                .build();

        verification.verify(attempt);

        assertThat(verification.isComplete()).isTrue();
        assertThat(verification.getCompleted()).contains(attempt.getCreated());
        assertThat(verification.getStatus()).isEqualTo(VerificationStatus.FAILED);
    }

    @Test
    void shouldCompleteVerificationIfAttemptIsSuccessfulAndThereAreStillAttemptsAreRemaining() {
        final String passcode = "12345678";
        final OneTimePasscodeVerification verification = OneTimePasscodeVerification.builder()
                .maxAttempts(2)
                .deliveries(OneTimePasscodeDeliveryMother.oneSmsDelivery(passcode))
                .build();
        final OneTimePasscodeVerificationAttempt attempt = OneTimePasscodeVerificationAttemptMother.builder()
                .passcode(passcode)
                .build();

        verification.verify(attempt);

        assertThat(verification.isComplete()).isTrue();
        assertThat(verification.getCompleted()).contains(attempt.getCreated());
        assertThat(verification.getStatus()).isEqualTo(VerificationStatus.SUCCESSFUL);
    }

    @Test
    void shouldNotCompleteVerificationOrUpdateStatusIfDeliveryHasExpired() {
        final String passcode = "12345678";
        final OneTimePasscodeDelivery delivery = OneTimePasscodeDeliveryMother.smsDelivery(passcode);
        final OneTimePasscodeVerification verification = OneTimePasscodeVerification.builder()
                .maxAttempts(3)
                .deliveries(Collections.singleton(delivery))
                .build();
        final OneTimePasscodeVerificationAttempt attempt = OneTimePasscodeVerificationAttemptMother.builder()
                .created(delivery.getExpiry().plusMillis(1))
                .build();

        verification.verify(Arrays.asList(attempt, attempt));

        assertThat(verification.isComplete()).isFalse();
        assertThat(verification.getCompleted()).isEmpty();
        assertThat(verification.getStatus()).isEqualTo(VerificationStatus.PENDING);
    }

    @Test
    void shouldNotCompleteVerificationOrUpdateStatusIfAttemptFailedAndHasAttemptRemaining() {
        final String passcode = "12345678";
        final OneTimePasscodeVerification verification = OneTimePasscodeVerification.builder()
                .maxAttempts(3)
                .deliveries(OneTimePasscodeDeliveryMother.oneSmsDelivery(passcode))
                .build();
        final OneTimePasscodeVerificationAttempt attempt = OneTimePasscodeVerificationAttemptMother.builder()
                .passcode("222222")
                .build();

        verification.verify(Arrays.asList(attempt, attempt));

        assertThat(verification.isComplete()).isFalse();
        assertThat(verification.getCompleted()).isEmpty();
        assertThat(verification.getStatus()).isEqualTo(VerificationStatus.PENDING);
    }

    @Test
    void shouldNotBeSuccessfulIfStatusIsPending() {
        final OneTimePasscodeVerification verification = OneTimePasscodeVerification.builder()
                .status(VerificationStatus.PENDING)
                .build();

        assertThat(verification.isSuccessful()).isFalse();
    }

    @Test
    void shouldNotBeSuccessfulIfStatusIsFailed() {
        final OneTimePasscodeVerification verification = OneTimePasscodeVerification.builder()
                .status(VerificationStatus.FAILED)
                .build();

        assertThat(verification.isSuccessful()).isFalse();
    }

    @Test
    void shouldBeSuccessfulIfStatusIsSuccessful() {
        final OneTimePasscodeVerification verification = OneTimePasscodeVerification.builder()
                .status(VerificationStatus.SUCCESSFUL)
                .build();

        assertThat(verification.isSuccessful()).isTrue();
    }

}
