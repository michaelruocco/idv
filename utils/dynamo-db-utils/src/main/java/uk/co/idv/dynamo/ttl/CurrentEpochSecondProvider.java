package uk.co.idv.dynamo.ttl;

import java.time.Instant;

public class CurrentEpochSecondProvider implements EpochSecondProvider {

    public long now() {
        return Instant.now().getEpochSecond();
    }

}
