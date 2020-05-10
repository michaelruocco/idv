package uk.co.idv.domain.entities.verificationcontext.sequence;

import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.OneTimePasscodeMother;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.mobile.MobilePinsentryMother;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical.PhysicalPinsentryMother;
import uk.co.idv.domain.entities.verificationcontext.method.pushnotification.PushNotificationMother;

import java.util.Arrays;
import java.util.Collection;

public class VerificationSequenceMother {

    public static VerificationSequence oneEligibleMethodSequence() {
        return new SingleMethodSequence(PushNotificationMother.eligible());
    }

    public static VerificationSequence twoEligibleMethodSequence() {
        final Collection<VerificationMethod> methods = Arrays.asList(
                PushNotificationMother.eligible(),
                MobilePinsentryMother.eligible()
        );
        return new MultipleMethodSequence("multiple-method-sequence", methods);
    }

    public static VerificationSequences defaultEligibleMethodSequences() {
        final Collection<VerificationSequence> sequences = Arrays.asList(
                new SingleMethodSequence(PushNotificationMother.eligible()),
                new SingleMethodSequence(PhysicalPinsentryMother.eligible()),
                new SingleMethodSequence(MobilePinsentryMother.eligible()),
                new SingleMethodSequence(OneTimePasscodeMother.eligible())
        );
        return new VerificationSequences(sequences);
    }

    public static VerificationSequences defaultIneligibleMethodSequences() {
        final Collection<VerificationSequence> sequences = Arrays.asList(
                new SingleMethodSequence(PushNotificationMother.ineligible()),
                new SingleMethodSequence(PhysicalPinsentryMother.ineligible()),
                new SingleMethodSequence(MobilePinsentryMother.ineligible()),
                new SingleMethodSequence(OneTimePasscodeMother.ineligible())
        );
        return new VerificationSequences(sequences);
    }

}
