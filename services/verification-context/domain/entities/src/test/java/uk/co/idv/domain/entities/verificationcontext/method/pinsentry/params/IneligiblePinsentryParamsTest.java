package uk.co.idv.domain.entities.verificationcontext.method.pinsentry.params;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

class IneligiblePinsentryParamsTest {

    private static final PinsentryFunction FUNCTION = PinsentryFunction.RESPOND;

    private final PinsentryParams params = new IneligiblePinsentryParams(FUNCTION);

    @Test
    void shouldReturnZeroMaxAttempts() {
        assertThat(params.getMaxAttempts()).isEqualTo(0);
    }

    @Test
    void shouldReturnDuration() {
        assertThat(params.getDuration()).isEqualTo(Duration.ZERO);
    }

    @Test
    void shouldReturnFunction() {
        assertThat(params.getFunction()).isEqualTo(FUNCTION);
    }

    @Test
    void shouldTestEquals() {
        EqualsVerifier.forClass(IneligiblePinsentryParams.class)
                .withRedefinedSuperclass()
                .suppress(Warning.STRICT_INHERITANCE)
                .verify();
    }

}
