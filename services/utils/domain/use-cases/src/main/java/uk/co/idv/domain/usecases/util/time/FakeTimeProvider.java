package uk.co.idv.domain.usecases.util.time;

import java.time.Instant;

public class FakeTimeProvider implements TimeProvider {

    private final Instant now;

    public FakeTimeProvider(final Instant now) {
        this.now = now;
    }

    @Override
    public Instant now() {
        return now;
    }

}
