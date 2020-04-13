package uk.co.idv.domain.usecases.util;

import java.time.Instant;

public class FakeTimeProvider implements TimeProvider {

    private Instant now;

    public FakeTimeProvider(final Instant now) {
        this.now = now;
    }

    @Override
    public Instant now() {
        return now;
    }

    public void setNow(final Instant now) {
        this.now = now;
    }

}
