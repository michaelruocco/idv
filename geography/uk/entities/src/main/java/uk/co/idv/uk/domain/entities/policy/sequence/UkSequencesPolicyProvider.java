package uk.co.idv.uk.domain.entities.policy.sequence;

import lombok.RequiredArgsConstructor;
import uk.co.idv.domain.entities.verificationcontext.sequence.policy.VerificationSequencesPolicy;
import uk.co.idv.domain.entities.verificationcontext.sequence.policy.VerificationSequencesPolicyProvider;
import uk.co.idv.uk.domain.entities.policy.sequence.rsa.RsaOnlinePurchasePolicy;

import java.util.Collection;
import java.util.Collections;

@RequiredArgsConstructor
public class UkSequencesPolicyProvider implements VerificationSequencesPolicyProvider {

    @Override
    public Collection<VerificationSequencesPolicy> getPolicies() {
        return Collections.singleton(new RsaOnlinePurchasePolicy());
    }

}
