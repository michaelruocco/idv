package uk.co.idv.uk.domain.entities.policy.sequence;

import lombok.RequiredArgsConstructor;
import uk.co.idv.domain.entities.verificationcontext.policy.VerificationPolicy;
import uk.co.idv.domain.entities.verificationcontext.policy.VerificationPolicyProvider;
import uk.co.idv.uk.domain.entities.policy.sequence.rsa.RsaOnlinePurchasePolicy;

import java.util.Collection;
import java.util.Collections;

@RequiredArgsConstructor
public class UkVerificationPolicyProvider implements VerificationPolicyProvider {

    @Override
    public Collection<VerificationPolicy> getPolicies() {
        return Collections.singleton(new RsaOnlinePurchasePolicy());
    }

}
