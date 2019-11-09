package uk.co.idv.domain.entities.activity;


import java.time.Instant;

public class FakeActivity implements Activity {

    private final Instant timestamp;

    public FakeActivity() {
        this(Instant.now());
    }

    public FakeActivity(final Instant timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String getName() {
        return "fake-activity";
    }

    @Override
    public Instant getTimestamp() {
        return timestamp;
    }

}
