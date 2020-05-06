package uk.co.idv.uk.domain.entities.policy.sequence.rsa;

import uk.co.idv.domain.entities.verificationcontext.sequence.policy.DefaultVerificationSequencesPolicy;
import uk.co.idv.domain.entities.verificationcontext.sequence.policy.VerificationSequencePolicy;

import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

public class RsaOnlinePurchasePolicy extends DefaultVerificationSequencesPolicy {

    public RsaOnlinePurchasePolicy() {
        super(
                UUID.fromString("8072b821-9945-49c3-b299-819ca085f2a4"),
                new RsaOnlinePurchasePolicyLevel(),
                buildSequencePolicies()
        );
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
