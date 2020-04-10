package uk.co.idv.domain.entities.activity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Instant;

@Data
@Builder
@EqualsAndHashCode
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
