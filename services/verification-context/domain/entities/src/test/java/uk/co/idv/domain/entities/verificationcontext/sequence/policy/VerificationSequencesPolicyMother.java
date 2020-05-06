package uk.co.idv.domain.entities.verificationcontext.sequence.policy;

import uk.co.idv.domain.entities.policy.PolicyLevelMother;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.policy.OneTimePasscodePolicyMother;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.mobile.policy.MobilePinsentryPolicyMother;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical.policy.PhysicalPinsentryPolicyMother;
import uk.co.idv.domain.entities.verificationcontext.method.pushnotification.policy.PushNotificationPolicyMother;

import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

public class VerificationSequencesPolicyMother {

    public static VerificationSequencesPolicy build() {
        return new DefaultVerificationSequencesPolicy(
                UUID.fromString("a94c6385-cbfd-4823-8a8c-d47c7b274bdf"),
                PolicyLevelMother.defaultPolicyLevel(),
                buildSequencePolicies()
        );
    }

    private static Collection<VerificationSequencePolicy> buildSequencePolicies() {
        return Arrays.asList(
                new VerificationSequencePolicy(PushNotificationPolicyMother.build()),
                new VerificationSequencePolicy(MobilePinsentryPolicyMother.build()),
                new VerificationSequencePolicy(OneTimePasscodePolicyMother.build()),
                new VerificationSequencePolicy(PhysicalPinsentryPolicyMother.build())
        );
    }
}
