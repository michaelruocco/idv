package uk.co.idv.domain.entities.verificationcontext.method.pinsentry;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

class PinsentryParamsTest {

    @Test
    void shouldReturnMaxAttempts() {
        final int maxAttempts = 5;

        final PinsentryParams params = PinsentryParams.builder()
                .maxAttempts(maxAttempts)
                .build();

        assertThat(params.getMaxAttempts()).isEqualTo(maxAttempts);
    }

    @Test
    void shouldReturnDuration() {
        final Duration duration = Duration.ofMinutes(1);

        final PinsentryParams params = PinsentryParams.builder()
                .duration(duration)
                .build();

        assertThat(params.getDuration()).isEqualTo(duration);
    }

    @Test
    void shouldReturnFunction() {
        final PinsentryFunction function = PinsentryFunction.RESPOND;

        final PinsentryParams params = PinsentryParams.builder()
                .function(function)
                .build();

        assertThat(params.getFunction()).isEqualTo(function);
    }

    @Test
    void shouldTestEquals() {
        EqualsVerifier.forClass(PinsentryParams.class)
                .suppress(Warning.STRICT_INHERITANCE)
                .verify();
    }

}
