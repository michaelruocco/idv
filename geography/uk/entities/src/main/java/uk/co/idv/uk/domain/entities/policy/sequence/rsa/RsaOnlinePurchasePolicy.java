package uk.co.idv.uk.domain.entities.policy.sequence.rsa;

import uk.co.idv.domain.entities.verificationcontext.sequence.policy.DefaultVerificationSequencesPolicy;
import uk.co.idv.domain.entities.verificationcontext.sequence.policy.VerificationSequencePolicy;

import java.util.Arrays;
import java.util.Collection;

public class RsaOnlinePurchasePolicy extends DefaultVerificationSequencesPolicy {

    public RsaOnlinePurchasePolicy() {
        super(new RsaOnlinePurchasePolicyLevel(), buildSequencePolicies());
    }

    private static Collection<VerificationSequencePolicy> buildSequencePolicies() {
        return Arrays.asList(
                new VerificationSequencePolicy(new RsaPushNotificationPolicy()),
                new VerificationSequencePolicy(new RsaMobilePinsentryPolicy()),
                new VerificationSequencePolicy(new RsaOneTimePasscodePolicy()),
                new VerificationSequencePolicy(new RsaPhysicalPinsentryPolicy())
        );
    }

}
