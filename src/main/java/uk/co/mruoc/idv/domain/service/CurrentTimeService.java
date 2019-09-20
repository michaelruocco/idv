package uk.co.mruoc.idv.domain.service;

import java.time.Instant;

public class CurrentTimeService implements TimeService {

    @Override
    public Instant now() {
        return Instant.now();
    }

}
