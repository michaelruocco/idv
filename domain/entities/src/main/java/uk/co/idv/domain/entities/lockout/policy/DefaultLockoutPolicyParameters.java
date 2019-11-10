package uk.co.idv.domain.entities.lockout.policy;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class DefaultLockoutPolicyParameters implements LockoutPolicyParameters {

    private final UUID id;
    private final String lockoutType;
    private final String recordAttemptStrategyType;
    private final LockoutLevel lockoutLevel;

}
