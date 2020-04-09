package uk.co.idv.dynamo.ttl;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TimeToLiveCalculator {

    private final EpochSecondProvider epochSecondProvider;
    private final long timeToLive;

    public long calculate() {
        return epochSecondProvider.now() + timeToLive;
    }

}
