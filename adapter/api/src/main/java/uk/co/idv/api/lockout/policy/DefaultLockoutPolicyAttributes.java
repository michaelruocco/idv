package uk.co.idv.api.lockout.policy;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import uk.co.idv.domain.entities.lockout.policy.LockoutLevel;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class DefaultLockoutPolicyAttributes implements LockoutPolicyAttributes {

    private final UUID id;
    private final String lockoutType;
    private final String recordAttempts;
    private final LockoutLevel lockoutLevel;

}
