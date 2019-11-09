package uk.co.idv.domain.usecases.util;

import java.time.Instant;

public class CurrentTimeGenerator implements TimeGenerator {

    @Override
    public Instant now() {
        return Instant.now();
    }

}
