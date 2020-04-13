package uk.co.idv.domain.usecases.util.time;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

public class FakeTimeProviderTest {

    private final Instant now = Instant.now();

    private final TimeProvider provider = new FakeTimeProvider(now);

    @Test
    void shouldReturnFixedTime() {
        assertThat(provider.now()).isEqualTo(now);
    }

}
