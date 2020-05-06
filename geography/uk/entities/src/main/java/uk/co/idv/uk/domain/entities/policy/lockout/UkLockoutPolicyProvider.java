package uk.co.idv.uk.domain.entities.policy.lockout;

import lombok.RequiredArgsConstructor;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicyProvider;
import uk.co.idv.uk.domain.entities.policy.lockout.as3.As3LockoutPolicy;
import uk.co.idv.uk.domain.entities.policy.lockout.rsa.RsaLockoutPolicy;

import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

@RequiredArgsConstructor
public class UkLockoutPolicyProvider implements LockoutPolicyProvider {

    @Override
    public Collection<LockoutPolicy> getPolicies() {
        return Arrays.asList(
                new RsaLockoutPolicy(UUID.fromString("d3bf531a-bdcd-45d5-b5b6-d7a213f3af7b")),
                new As3LockoutPolicy(UUID.fromString("4c60fce6-0dc9-4ec5-8ba8-b7706d67007a"))
        );
    }

}
