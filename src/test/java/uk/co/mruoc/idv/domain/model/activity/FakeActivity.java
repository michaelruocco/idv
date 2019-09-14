package uk.co.mruoc.idv.domain.model.activity;

import java.time.Instant;

public class FakeActivity implements Activity {

    private final Instant now;

    public FakeActivity(final Instant now) {
        this.now = now;
    }

    @Override
    public String getName() {
        return "fake-activity";
    }

    @Override
    public Instant getTimestamp() {
        return now;
    }

}
