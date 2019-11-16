package uk.co.idv.uk.domain.entities.lockout;

import lombok.RequiredArgsConstructor;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.entities.identity.alias.CreditCardNumber;
import uk.co.idv.domain.entities.identity.alias.DebitCardNumber;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicyProvider;

import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

@RequiredArgsConstructor
public class UkLockoutPolicyProvider implements LockoutPolicyProvider {

    @Override
    public Collection<LockoutPolicy> getPolicies() {
        return Arrays.asList(
                new RsaLockoutPolicy(UUID.fromString("d3bf531a-bdcd-45d5-b5b6-d7a213f3af7b"), CreditCardNumber.TYPE),
                new RsaLockoutPolicy(UUID.fromString("455a23f9-6505-491b-aa0f-2d4bf06acbbe"), DebitCardNumber.TYPE)
        );
    }

}
