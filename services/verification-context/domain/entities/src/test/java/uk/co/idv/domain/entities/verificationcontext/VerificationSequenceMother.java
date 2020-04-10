package uk.co.idv.domain.entities.verificationcontext;

import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.PinsentryFunction;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.mobile.MobilePinsentryEligible;
import uk.co.idv.domain.entities.verificationcontext.method.pushnotification.PushNotificationEligible;

import java.util.Arrays;
import java.util.Collection;

public class VerificationSequenceMother {

    public static VerificationSequence singleMethodSequence() {
        return new SingleMethodSequence(new PushNotificationEligible());
    }

    public static VerificationSequence multipleMethodSequence() {
        final Collection<VerificationMethod> methods = buildMultipleMethods();
        return new MultipleMethodSequence("multiple-method-sequence", methods);
    }

    private static Collection<VerificationMethod> buildMultipleMethods() {
        return Arrays.asList(
                new PushNotificationEligible(),
                new MobilePinsentryEligible(PinsentryFunction.RESPOND)
        );
    }

}
