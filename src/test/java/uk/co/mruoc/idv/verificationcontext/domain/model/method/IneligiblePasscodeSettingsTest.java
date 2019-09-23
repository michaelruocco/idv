package uk.co.mruoc.idv.verificationcontext.domain.model.method;

import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

class IneligiblePasscodeSettingsTest {

    private final PasscodeSettings settings = new IneligiblePasscodeSettings();

    @Test
    void shouldReturnLength() {
        assertThat(settings.getLength()).isEqualTo(0);
    }

    @Test
    void shouldReturnDuration() {
        assertThat(settings.getDuration()).isEqualTo(Duration.ZERO);
    }

    @Test
    void shouldReturnMaxAttempts() {
        assertThat(settings.getMaxAttempts()).isEqualTo(0);
    }

}
