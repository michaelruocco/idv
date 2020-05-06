package uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.params;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class OneTimePasscodeParamsTest {

    @Test
    void shouldReturnMaxAttempts() {
        final int maxAttempts = 5;

        final OneTimePasscodeParams params = DefaultOneTimePasscodeParams.builder()
                .maxAttempts(maxAttempts)
                .build();

        assertThat(params.getMaxAttempts()).isEqualTo(maxAttempts);
    }

    @Test
    void shouldReturnDuration() {
        final Duration duration = Duration.ofMinutes(1);

        final OneTimePasscodeParams params = DefaultOneTimePasscodeParams.builder()
                .duration(duration)
                .build();

        assertThat(params.getDuration()).isEqualTo(duration);
    }

    @Test
    void shouldReturnPasscodeSettings() {
        final PasscodeParams passcodeParams = mock(PasscodeParams.class);

        final OneTimePasscodeParams params = DefaultOneTimePasscodeParams.builder()
                .passcodeParams(passcodeParams)
                .build();

        assertThat(params.getPasscodeParams()).isEqualTo(passcodeParams);
    }

    @Test
    void shouldReturnLengthFromPasscodeSettings() {
        final int length = 4;
        final PasscodeParams passcodeParams = mock(PasscodeParams.class);
        given(passcodeParams.getLength()).willReturn(length);

        final OneTimePasscodeParams params = DefaultOneTimePasscodeParams.builder()
                .passcodeParams(passcodeParams)
                .build();

        assertThat(params.getPasscodeLength()).isEqualTo(length);
    }

    @Test
    void shouldReturnMaxDeliveriesFromPasscodeSettings() {
        final int maxDeliveries = 2;
        final PasscodeParams passcodeParams = mock(PasscodeParams.class);
        given(passcodeParams.getMaxDeliveries()).willReturn(maxDeliveries);

        final OneTimePasscodeParams params = DefaultOneTimePasscodeParams.builder()
                .passcodeParams(passcodeParams)
                .build();

        assertThat(params.getMaxDeliveries()).isEqualTo(maxDeliveries);
    }

    @Test
    void shouldTestEquals() {
        EqualsVerifier.forClass(PasscodeParams.class)
                .suppress(Warning.STRICT_INHERITANCE)
                .verify();
    }

}
