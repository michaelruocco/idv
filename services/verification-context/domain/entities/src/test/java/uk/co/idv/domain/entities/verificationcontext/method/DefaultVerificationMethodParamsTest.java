package uk.co.idv.domain.entities.verificationcontext.method;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultVerificationMethodParamsTest {

    @Test
    void shouldReturnMaxAttempts() {
        final int maxAttempts = 5;

        final VerificationMethodParams params = uk.co.idv.domain.entities.verificationcontext.method.PinsentryParams.builder()
                .maxAttempts(maxAttempts)
                .build();

        assertThat(params.getMaxAttempts()).isEqualTo(maxAttempts);
    }

    @Test
    void shouldReturnDuration() {
        final Duration duration = Duration.ofMinutes(1);

        final VerificationMethodParams params = uk.co.idv.domain.entities.verificationcontext.method.PinsentryParams.builder()
                .duration(duration)
                .build();

        assertThat(params.getDuration()).isEqualTo(duration);
    }

    @Test
    void shouldTestEquals() {
        EqualsVerifier.forClass(uk.co.idv.domain.entities.verificationcontext.method.PinsentryParams.class)
                .suppress(Warning.STRICT_INHERITANCE)
                .verify();
    }

}
