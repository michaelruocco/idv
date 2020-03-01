package uk.co.idv.domain.entities.verification.onetimepasscode;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.verification.VerificationStatus;
import uk.co.idv.domain.entities.verification.onetimepasscode.exception.NoGenerationsRemainingException;
import uk.co.idv.domain.entities.verification.onetimepasscode.exception.VerificationAlreadyCompleteException;

import java.time.Instant;
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
    void shouldReturnPasscode() {
        final String passcode = "12345678";

        final OneTimePasscodeVerification verification = OneTimePasscodeVerification.builder()
                .passcode(passcode)
                .build();

        assertThat(verification.getPasscode()).isEqualTo(passcode);
    }

    @Test
    void shouldReturnMaxGenerations() {
        final int maxGenerations = 3;

        final OneTimePasscodeVerification verification = OneTimePasscodeVerification.builder()
                .maxGenerations(maxGenerations)
                .build();

        assertThat(verification.getMaxGenerations()).isEqualTo(maxGenerations);
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
    void shouldReturnEmptyGenerationsByDefault() {
        final OneTimePasscodeVerification verification = OneTimePasscodeVerification.builder()
                .build();

        assertThat(verification.getGenerations()).isEmpty();
    }

    @Test
    void shouldReturnEmptyAttmeptsByDefault() {
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
    void shouldThrowExceptionIfGenerationIsRecordedWhenVerificationIsComplete() {
        final OneTimePasscodeVerification verification = OneTimePasscodeVerification.builder()
                .completed(Instant.now())
                .build();
        final OneTimePasscodeGeneration generation = OneTimePasscodeGenerationMother.smsGeneration();

        final Throwable error = catchThrowable(() -> verification.record(generation));

        assertThat(error).isInstanceOf(VerificationAlreadyCompleteException.class);
    }

    @Test
    void shouldThrowExceptionIfGenerationIsRecordedWhenNoGenerationsAreRemaining() {
        final OneTimePasscodeVerification verification = OneTimePasscodeVerification.builder()
                .maxGenerations(1)
                .build();
        final OneTimePasscodeGeneration generation = OneTimePasscodeGenerationMother.smsGeneration();
        verification.record(generation);

        final Throwable error = catchThrowable(() -> verification.record(generation));

        assertThat(error).isInstanceOf(NoGenerationsRemainingException.class);
    }

    @Test
    void shouldDecrementGenerationsRemainingAfterGenerationIsRecorded() {
        final int maxGenerations = 1;
        final OneTimePasscodeVerification verification = OneTimePasscodeVerification.builder()
                .maxGenerations(maxGenerations)
                .build();
        final OneTimePasscodeGeneration generation = OneTimePasscodeGenerationMother.smsGeneration();

        assertThat(verification.getGenerationsRemaining()).isEqualTo(maxGenerations);
        verification.record(generation);
        assertThat(verification.getGenerationsRemaining()).isEqualTo(maxGenerations - 1);
    }

    @Test
    void shouldReturnRecordedGenerations() {
        final OneTimePasscodeVerification verification = OneTimePasscodeVerification.builder()
                .maxGenerations(1)
                .build();
        final OneTimePasscodeGeneration generation = OneTimePasscodeGenerationMother.smsGeneration();

        verification.record(generation);

        assertThat(verification.getGenerations()).containsExactly(generation);
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
                .generations(OneTimePasscodeGenerationMother.oneSmsGeneration("111111"))
                .build();
        final OneTimePasscodeVerificationAttempt attempt = OneTimePasscodeVerificationAttemptMother.attempt("222222");

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
                .generations(OneTimePasscodeGenerationMother.oneSmsGeneration(passcode))
                .build();
        final OneTimePasscodeVerificationAttempt attempt = OneTimePasscodeVerificationAttemptMother.attempt(passcode);

        verification.verify(attempt);

        assertThat(verification.isComplete()).isTrue();
        assertThat(verification.getCompleted()).contains(attempt.getCreated());
        assertThat(verification.getStatus()).isEqualTo(VerificationStatus.SUCCESSFUL);
    }

}
