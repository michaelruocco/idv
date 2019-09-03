package uk.co.mruoc.verificationcontext.domain.method;

import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultPasscodeSettingsTest {

    private final PasscodeSettings settings = new DefaultPasscodeSettings();

    @Test
    void shouldReturnLength() {
        assertThat(settings.getLength()).isEqualTo(8);
    }

    @Test
    void shouldReturnDuration() {
        assertThat(settings.getDuration()).isEqualTo(Duration.ofMillis(150000));
    }

    @Test
    void shouldReturnMaxAttempts() {
        assertThat(settings.getMaxAttempts()).isEqualTo(3);
    }

}
