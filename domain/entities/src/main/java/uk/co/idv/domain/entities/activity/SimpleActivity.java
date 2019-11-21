package uk.co.idv.domain.entities.activity;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class SimpleActivity implements Activity {

    private final String name;
    private final Instant timestamp;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Instant getTimestamp() {
        return timestamp;
    }

}
