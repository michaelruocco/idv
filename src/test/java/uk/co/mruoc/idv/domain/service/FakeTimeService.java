package uk.co.mruoc.idv.domain.service;

import java.time.Instant;

public class FakeTimeService implements TimeService {

    private final Instant now;

    public FakeTimeService(final Instant now) {
        this.now = now;
    }

    @Override
    public Instant now() {
        return now;
    }

}