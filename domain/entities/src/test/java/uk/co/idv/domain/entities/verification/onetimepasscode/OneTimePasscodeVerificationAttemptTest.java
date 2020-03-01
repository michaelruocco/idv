package uk.co.idv.domain.entities.verification.onetimepasscode;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class OneTimePasscodeVerificationAttemptTest {

    @Test
    void shouldReturnCreated() {
        final Instant created = Instant.now();

        final OneTimePasscodeVerificationAttempt attempt = OneTimePasscodeVerificationAttempt.builder()
                .created(created)
                .build();

        assertThat(attempt.getCreated()).isEqualTo(created);
    }

    @Test
    void shouldReturnPasscode() {
        final String passcode = "12345678";

        final OneTimePasscodeVerificationAttempt attempt = OneTimePasscodeVerificationAttempt.builder()
                .passcode(passcode)
                .build();

        assertThat(attempt.getPasscode()).isEqualTo(passcode);
    }

}
