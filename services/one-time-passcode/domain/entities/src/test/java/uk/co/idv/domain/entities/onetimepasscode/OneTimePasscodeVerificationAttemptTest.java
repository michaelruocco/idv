package uk.co.idv.domain.entities.onetimepasscode;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
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

    @Test
    void shouldTestEquals() {
        EqualsVerifier.forClass(OneTimePasscodeVerificationAttempt.class)
                .suppress(Warning.STRICT_INHERITANCE)
                .verify();
    }

}
