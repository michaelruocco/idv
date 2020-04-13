package uk.co.idv.domain.usecases.util.time;


import java.time.Instant;

public class CurrentTimeProvider implements TimeProvider {

    @Override
    public Instant now() {
        return Instant.now();
    }

}
