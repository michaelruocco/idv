package uk.co.idv.dynamo.ttl;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class TimeToLiveCalculatorTest {

    private static final long TIME_TO_LIVE_SECONDS = 100;

    private final EpochSecondProvider epochSecondProvider = mock(EpochSecondProvider.class);

    private final TimeToLiveCalculator calculator = new TimeToLiveCalculator(epochSecondProvider, TIME_TO_LIVE_SECONDS);

    @Test
    void shouldReturnCurrentEpochSecondPlusTimeToLiveSeconds() {
        final Instant now = Instant.now();
        given(epochSecondProvider.now()).willReturn(now.getEpochSecond());

        final long timeToLive = calculator.calculate();

        assertThat(timeToLive).isEqualTo(now.getEpochSecond() + TIME_TO_LIVE_SECONDS);
    }

}
