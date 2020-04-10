package uk.co.idv.domain.usecases.util;

import java.time.Instant;

public class FakeTimeGenerator implements TimeGenerator {

    private Instant now;

    public FakeTimeGenerator(final Instant now) {
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
