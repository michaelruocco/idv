package uk.co.idv.repository.dynamo;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.usecases.util.TimeGenerator;
import uk.co.idv.repository.dynamo.table.TimeToLiveCalculator;

import java.time.Duration;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class OneHourTimeToLiveCalculatorTest {

    private final TimeGenerator timeGenerator = mock(TimeGenerator.class);

    private final TimeToLiveCalculator calculator = new OneHourTimeToLiveCalculator(timeGenerator);

    @Test
    void shouldReturnCurrentEpochSecondPlusTimeToLiveSecondsOfOneHourInSeconds() {
        final Instant now = Instant.now();
        given(timeGenerator.now()).willReturn(now);

        final long timeToLive = calculator.calculate();

        final long expectedTimeToLiveSeconds = Duration.ofHours(1).toSeconds();
        assertThat(timeToLive).isEqualTo(now.getEpochSecond() + expectedTimeToLiveSeconds);
    }

}
