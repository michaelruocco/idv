package uk.co.idv.repository.dynamo.table;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.usecases.util.TimeGenerator;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class TimeToLiveCalculatorTest {

    private static final long TIME_TO_LIVE_SECONDS = 100;

    private final TimeGenerator timeGenerator = mock(TimeGenerator.class);

    private final TimeToLiveCalculator calculator = new TimeToLiveCalculator(timeGenerator, TIME_TO_LIVE_SECONDS);

    @Test
    void shouldReturnCurrentEpochSecondPlusTimeToLiveSeconds() {
        final Instant now = Instant.now();
        given(timeGenerator.now()).willReturn(now);

        final long timeToLive = calculator.calculate();

        assertThat(timeToLive).isEqualTo(now.getEpochSecond() + TIME_TO_LIVE_SECONDS);
    }

}
