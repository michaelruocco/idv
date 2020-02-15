package uk.co.idv.domain.entities.lockout.policy.soft;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.Duration;

@Getter
@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
public class SoftLockInterval {

    private final int numberOfAttempts;
    private final Duration duration;

}
