package uk.co.idv.domain.entities.verificationcontext.method.pinsentry.params;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

class PinsentryParamsTest {

    @Test
    void shouldReturnMaxAttempts() {
        final int maxAttempts = 5;

        final PinsentryParams params = DefaultPinsentryParams.builder()
                .maxAttempts(maxAttempts)
                .build();

        assertThat(params.getMaxAttempts()).isEqualTo(maxAttempts);
    }

    @Test
    void shouldReturnDuration() {
        final Duration duration = Duration.ofMinutes(1);

        final PinsentryParams params = DefaultPinsentryParams.builder()
                .duration(duration)
                .build();

        assertThat(params.getDuration()).isEqualTo(duration);
    }

    @Test
    void shouldReturnFunction() {
        final PinsentryFunction function = PinsentryFunction.RESPOND;

        final PinsentryParams params = DefaultPinsentryParams.builder()
                .function(function)
                .build();

        assertThat(params.getFunction()).isEqualTo(function);
    }

    @Test
    void shouldTestEquals() {
        EqualsVerifier.forClass(DefaultPinsentryParams.class)
                .suppress(Warning.STRICT_INHERITANCE)
                .verify();
    }

}
