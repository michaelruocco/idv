package uk.co.idv.repository.mongo.lockout.policy.soft;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import uk.co.idv.repository.mongo.lockout.policy.LockoutPolicyDocument;

@Data
@RequiredArgsConstructor
public class SoftLockIntervalDocument {

    private final int numberOfAttempts;
    private final long duration;

}
