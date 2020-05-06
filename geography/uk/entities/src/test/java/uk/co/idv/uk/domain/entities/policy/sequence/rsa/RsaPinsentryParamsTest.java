package uk.co.idv.uk.domain.entities.policy.sequence.rsa;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.params.PinsentryFunction;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.params.PinsentryParams;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

class RsaPinsentryParamsTest {

    private final PinsentryParams params = new RsaPinsentryParams();

    @Test
    void shouldReturnMaxAttempts() {
        assertThat(params.getMaxAttempts()).isEqualTo(3);
    }

    @Test
    void shouldReturnDuration() {
        assertThat(params.getDuration()).isEqualTo(Duration.ofMinutes(5));
    }

    @Test
    void shouldReturnFunction() {
        assertThat(params.getFunction()).isEqualTo(PinsentryFunction.RESPOND);
    }

}
