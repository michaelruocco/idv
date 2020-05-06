package uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.params;

import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultPasscodeParamsTest {

    @Test
    void shouldReturnLength() {
        final int length = 8;

        final PasscodeParams settings = DefaultPasscodeParams.builder()
                .length(length)
                .build();

        assertThat(settings.getLength()).isEqualTo(length);
    }

    @Test
    void shouldReturnDuration() {
        final Duration duration = Duration.ofMillis(150000);

        final PasscodeParams settings = DefaultPasscodeParams.builder()
                .duration(duration)
                .build();

        assertThat(settings.getDuration()).isEqualTo(duration);
    }

    @Test
    void shouldReturnMaxDeliveries() {
        final int maxDeliveries = 3;

        final PasscodeParams settings = DefaultPasscodeParams.builder()
                .maxDeliveries(maxDeliveries)
                .build();

        assertThat(settings.getMaxDeliveries()).isEqualTo(maxDeliveries);
    }

}
