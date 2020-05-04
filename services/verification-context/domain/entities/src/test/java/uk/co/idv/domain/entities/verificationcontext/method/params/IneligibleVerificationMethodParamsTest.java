package uk.co.idv.domain.entities.verificationcontext.method.params;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethodParams;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

class IneligibleVerificationMethodParamsTest {

    private final VerificationMethodParams params = new IneligibleVerificationMethodParams();

    @Test
    void shouldReturnZeroMaxAttempts() {
        assertThat(params.getMaxAttempts()).isEqualTo(0);
    }

    @Test
    void shouldReturnZeroDuration() {
        assertThat(params.getDuration()).isEqualTo(Duration.ZERO);
    }

    @Test
    void shouldTestEquals() {
        EqualsVerifier.forClass(IneligibleVerificationMethodParams.class)
                .withRedefinedSuperclass()
                .suppress(Warning.STRICT_INHERITANCE)
                .verify();
    }

}
