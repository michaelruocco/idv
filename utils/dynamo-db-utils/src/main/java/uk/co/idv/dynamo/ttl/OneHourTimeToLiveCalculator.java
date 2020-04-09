package uk.co.idv.dynamo.ttl;

import java.time.Duration;

public class OneHourTimeToLiveCalculator extends TimeToLiveCalculator {

    private static final long TIME_TO_LIVE = Duration.ofHours(1).toSeconds();

    public OneHourTimeToLiveCalculator(final EpochSecondProvider epochSecondProvider) {
        super(epochSecondProvider, TIME_TO_LIVE);
    }

}
