package uk.co.mruoc.idv.lockout.domain.model.uk;

import uk.co.idv.domain.entities.identity.CreditCardNumber;
import uk.co.idv.domain.entities.identity.DebitCardNumber;
import uk.co.mruoc.idv.lockout.domain.LockoutPolicyParametersProvider;
import uk.co.mruoc.idv.lockout.domain.model.LockoutPolicyParameters;

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
