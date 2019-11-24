package uk.co.idv.repository.mongo.lockout.policy.soft;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class SoftLockIntervalDocument {

    private final int numberOfAttempts;
    private final long duration;

}
