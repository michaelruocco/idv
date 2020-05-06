package uk.co.idv.domain.entities.verificationcontext.sequence;

import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.mobile.MobilePinsentryMother;
import uk.co.idv.domain.entities.verificationcontext.method.pushnotification.PushNotificationMother;

import java.util.Arrays;
import java.util.Collection;

public class VerificationSequenceMother {

    public static VerificationSequence singleMethodSequence() {
        return new SingleMethodSequence(PushNotificationMother.eligible());
    }

    public static VerificationSequence multipleMethodSequence() {
        final Collection<VerificationMethod> methods = buildMultipleMethods();
        return new MultipleMethodSequence("multiple-method-sequence", methods);
    }

    private static Collection<VerificationMethod> buildMultipleMethods() {
        return Arrays.asList(
                PushNotificationMother.eligible(),
                MobilePinsentryMother.eligible()
        );
    }

}
