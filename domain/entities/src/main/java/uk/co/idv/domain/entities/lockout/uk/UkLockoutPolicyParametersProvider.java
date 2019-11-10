package uk.co.idv.domain.entities.lockout.uk;

import uk.co.idv.domain.entities.lockout.policy.LockoutPolicyParameters;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicyParametersProvider;
import uk.co.idv.domain.entities.identity.alias.CreditCardNumber;
import uk.co.idv.domain.entities.identity.alias.DebitCardNumber;

import java.util.Arrays;
import java.util.Collection;

public class UkLockoutPolicyParametersProvider implements LockoutPolicyParametersProvider {

    @Override
    public Collection<LockoutPolicyParameters> getPolicies() {
        return Arrays.asList(
                new RsaMaxAttemptsAliasLevelLockoutPolicyParameters(CreditCardNumber.TYPE),
                new RsaMaxAttemptsAliasLevelLockoutPolicyParameters(DebitCardNumber.TYPE)
        );
    }

}
