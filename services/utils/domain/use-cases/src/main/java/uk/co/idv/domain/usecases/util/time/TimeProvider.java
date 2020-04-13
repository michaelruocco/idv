package uk.co.idv.domain.usecases.util.time;

import java.time.Instant;

public interface TimeProvider {

    Instant now();

}
