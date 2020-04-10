package uk.co.idv.api.lockout.policy.soft;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class SoftLockIntervalDto {

    private final int numberOfAttempts;
    private final long duration;

}
