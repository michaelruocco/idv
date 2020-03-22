package uk.co.idv.repository.dynamo;

import uk.co.idv.domain.usecases.util.TimeGenerator;
import uk.co.idv.repository.dynamo.table.TimeToLiveCalculator;

import java.time.Duration;

public class OneHourTimeToLiveCalculator extends TimeToLiveCalculator {

    private static final long TIME_TO_LIVE = Duration.ofHours(1).toSeconds();

    public OneHourTimeToLiveCalculator(final TimeGenerator timeGenerator) {
        super(timeGenerator, TIME_TO_LIVE);
    }

}
