package uk.co.idv.uk.domain.entities.lockout;

import lombok.RequiredArgsConstructor;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicyProvider;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

@RequiredArgsConstructor
public class UkLockoutPolicyProvider implements LockoutPolicyProvider {

    @Override
    public Collection<LockoutPolicy> getPolicies() {
        return Collections.singleton(
                new RsaLockoutPolicy(UUID.fromString("d3bf531a-bdcd-45d5-b5b6-d7a213f3af7b"))
        );
    }

}
