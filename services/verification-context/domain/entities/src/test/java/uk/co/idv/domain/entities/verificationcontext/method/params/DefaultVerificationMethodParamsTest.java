package uk.co.idv.domain.entities.verificationcontext.method.params;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethodParams;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultVerificationMethodParamsTest {

    @Test
    void shouldReturnMaxAttempts() {
        final int maxAttempts = 5;

        final VerificationMethodParams params = DefaultVerificationMethodParams.builder()
                .maxAttempts(maxAttempts)
                .build();

        assertThat(params.getMaxAttempts()).isEqualTo(maxAttempts);
    }

    @Test
    void shouldReturnDuration() {
        final Duration duration = Duration.ofMinutes(1);

        final VerificationMethodParams params = DefaultVerificationMethodParams.builder()
                .duration(duration)
                .build();

        assertThat(params.getDuration()).isEqualTo(duration);
    }

    @Test
    void shouldTestEquals() {
        EqualsVerifier.forClass(DefaultVerificationMethodParams.class)
                .suppress(Warning.STRICT_INHERITANCE)
                .verify();
    }

}
