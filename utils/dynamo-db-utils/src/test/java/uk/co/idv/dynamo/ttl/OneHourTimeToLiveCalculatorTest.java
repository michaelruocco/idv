package uk.co.idv.dynamo.ttl;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class OneHourTimeToLiveCalculatorTest {

    private final EpochSecondProvider epochSecondProvider = mock(EpochSecondProvider.class);

    private final TimeToLiveCalculator calculator = new OneHourTimeToLiveCalculator(epochSecondProvider);

    @Test
    void shouldReturnCurrentEpochSecondPlusTimeToLiveSecondsOfOneHourInSeconds() {
        final Instant now = Instant.now();
        given(epochSecondProvider.now()).willReturn(now.getEpochSecond());

        final long timeToLive = calculator.calculate();

        final long expectedTimeToLiveSeconds = Duration.ofHours(1).toSeconds();
        assertThat(timeToLive).isEqualTo(now.getEpochSecond() + expectedTimeToLiveSeconds);
    }

}
