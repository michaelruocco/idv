package uk.co.idv.repository.dynamo.table;

import lombok.RequiredArgsConstructor;
import uk.co.idv.domain.usecases.util.TimeGenerator;

@RequiredArgsConstructor
public class TimeToLiveCalculator {

    private final TimeGenerator timeGenerator;
    private final long timeToLive;

    public long calculate() {
        return timeGenerator.now().getEpochSecond() + timeToLive;
    }

}
