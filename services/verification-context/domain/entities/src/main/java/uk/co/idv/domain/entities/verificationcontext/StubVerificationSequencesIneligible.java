package uk.co.idv.domain.entities.verificationcontext;

import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.mobile.MobilePinsentryIneligible;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical.NoEligibleCards;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.OneTimePasscodeIneligible;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical.PhysicalPinsentryIneligible;
import uk.co.idv.domain.entities.verificationcontext.method.pushnotification.PushNotificationIneligible;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.PinsentryFunction;


public class StubVerificationSequencesIneligible extends VerificationSequences {

    private static final VerificationSequence PUSH_AUTHENTICATION = buildPushNotificationSequence();
    private static final VerificationSequence PHYSICAL_PINSENTRY = buildPhysicalPinsentrySequence();
    private static final VerificationSequence MOBILE_PINSENTRY = buildMobilePinsentrySequence();
    private static final VerificationSequence ONE_TIME_PASSCODE_SMS = buildOneTimePasscodeSmsSequence();

    public StubVerificationSequencesIneligible() {
        super(PUSH_AUTHENTICATION,
                PHYSICAL_PINSENTRY,
                MOBILE_PINSENTRY,
                ONE_TIME_PASSCODE_SMS
        );
    }

    private static VerificationSequence buildPushNotificationSequence() {
        final VerificationMethod pushNotification = new PushNotificationIneligible();
        return new SingleMethodSequence(pushNotification);
    }

    private static VerificationSequence buildPhysicalPinsentrySequence() {
        final VerificationMethod physicalPinsentry = new PhysicalPinsentryIneligible(new NoEligibleCards(), PinsentryFunction.RESPOND);
        return new SingleMethodSequence(physicalPinsentry);
    }

    private static VerificationSequence buildMobilePinsentrySequence() {
        final VerificationMethod mobilePinsentry = new MobilePinsentryIneligible(PinsentryFunction.RESPOND);
        return new SingleMethodSequence(mobilePinsentry);
    }

    private static VerificationSequence buildOneTimePasscodeSmsSequence() {
        final VerificationMethod oneTimePasscodeSms = new OneTimePasscodeIneligible();
        return new SingleMethodSequence(oneTimePasscodeSms);
    }

}
