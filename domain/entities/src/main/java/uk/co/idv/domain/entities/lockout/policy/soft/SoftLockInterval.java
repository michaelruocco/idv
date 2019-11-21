package uk.co.idv.domain.entities.lockout.policy.soft;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Duration;

@Getter
@RequiredArgsConstructor
public class SoftLockInterval {

    private final int numberOfAttempts;
    private final Duration duration;

}
