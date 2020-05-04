package uk.co.idv.domain.entities.verificationcontext;

import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.NoEligibleDeliveryMethods;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.OneTimePasscodeIneligible;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.NoMobileApplication;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.params.IneligiblePinsentryParams;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.mobile.MobilePinsentry;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical.NoEligibleCards;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.params.PinsentryFunction;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical.PhysicalPinsentry;
import uk.co.idv.domain.entities.verificationcontext.method.pushnotification.PushNotificationIneligible;

import java.util.Collections;

public class StubVerificationSequencesIneligible extends VerificationSequences {

    private static final VerificationSequence PUSH_AUTHENTICATION = buildPushNotificationSequence();
    private static final VerificationSequence PHYSICAL_PINSENTRY = buildPhysicalPinsentrySequence();
    private static final VerificationSequence MOBILE_PINSENTRY = buildMobilePinsentrySequence();
    private static final VerificationSequence ONE_TIME_PASSCODE = buildOneTimePasscodeSequence();

    public StubVerificationSequencesIneligible() {
        super(PUSH_AUTHENTICATION,
                PHYSICAL_PINSENTRY,
                MOBILE_PINSENTRY,
                ONE_TIME_PASSCODE
        );
    }

    private static VerificationSequence buildPushNotificationSequence() {
        final VerificationMethod pushNotification = new PushNotificationIneligible(new NoMobileApplication());
        return new SingleMethodSequence(pushNotification);
    }

    private static VerificationSequence buildPhysicalPinsentrySequence() {
        final VerificationMethod physicalPinsentry = PhysicalPinsentry.ineligibleBuilder()
                .params(new IneligiblePinsentryParams(PinsentryFunction.RESPOND))
                .eligibility(new NoEligibleCards())
                .cardNumbers(Collections.emptyList())
                .build();
        return new SingleMethodSequence(physicalPinsentry);
    }

    private static VerificationSequence buildMobilePinsentrySequence() {
        final VerificationMethod mobilePinsentry = MobilePinsentry.eligibleBuilder()
                .params(new IneligiblePinsentryParams(PinsentryFunction.RESPOND))
                .eligibility(new NoMobileApplication())
                .build();
        return new SingleMethodSequence(mobilePinsentry);
    }

    private static VerificationSequence buildOneTimePasscodeSequence() {
        final VerificationMethod oneTimePasscode = new OneTimePasscodeIneligible(
                new NoEligibleDeliveryMethods(),
                Collections.emptyList()
        );
        return new SingleMethodSequence(oneTimePasscode);
    }

}
