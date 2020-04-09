package uk.co.idv.dynamo.ttl;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CurrentEpochSecondProviderTest {

    private final EpochSecondProvider provider = new CurrentEpochSecondProvider();

    @Test
    void shouldReturnCurrentTime() {
        final long epochSecond1 = provider.now();
        final long epochSecond2 = provider.now();

        assertThat(epochSecond2).isGreaterThanOrEqualTo(epochSecond1);
    }

}
