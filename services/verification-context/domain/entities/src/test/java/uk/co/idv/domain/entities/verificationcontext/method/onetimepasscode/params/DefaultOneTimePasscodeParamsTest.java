package uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.params;

import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultOneTimePasscodeParamsTest {

    @Test
    void shouldReturnPasscodeSettings() {
        final PasscodeSettings settings = PasscodeSettingsMother.eligible();
        final OneTimePasscodeParams params = DefaultOneTimePasscodeParams.builder()
                .passcodeSettings(settings)
                .build();

        assertThat(params.getPasscodeSettings()).isEqualTo(settings);
    }

    @Test
    void shouldReturnPasscodeLength() {
        final PasscodeSettings settings = PasscodeSettingsMother.eligible();
        final OneTimePasscodeParams params = DefaultOneTimePasscodeParams.builder()
                .passcodeSettings(settings)
                .build();

        assertThat(params.getPasscodeLength()).isEqualTo(settings.getLength());
    }

    @Test
    void shouldReturnPasscodeDuration() {
        final PasscodeSettings settings = PasscodeSettingsMother.eligible();
        final OneTimePasscodeParams params = DefaultOneTimePasscodeParams.builder()
                .passcodeSettings(settings)
                .build();

        assertThat(params.getPasscodeDuration()).isEqualTo(settings.getDuration());
    }

    @Test
    void shouldReturnMaxDeliveries() {
        final PasscodeSettings settings = PasscodeSettingsMother.eligible();
        final OneTimePasscodeParams params = DefaultOneTimePasscodeParams.builder()
                .passcodeSettings(settings)
                .build();

        assertThat(params.getMaxDeliveries()).isEqualTo(settings.getMaxDeliveries());
    }

    @Test
    void shouldReturnMaxAttempts() {
        final int maxAttempts = 3;
        final OneTimePasscodeParams params = DefaultOneTimePasscodeParams.builder()
                .maxAttempts(maxAttempts)
                .build();

        assertThat(params.getMaxAttempts()).isEqualTo(maxAttempts);
    }

    @Test
    void shouldReturnDuration() {
        final Duration duration = Duration.ofMinutes(5);
        final OneTimePasscodeParams params = DefaultOneTimePasscodeParams.builder()
                .duration(duration)
                .build();

        assertThat(params.getDuration()).isEqualTo(duration);
    }

}
