package uk.co.idv.domain.entities.verificationcontext;

import uk.co.idv.domain.entities.verificationcontext.method.IneligibleVerificationMethodParams;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.NoMobileApplication;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.IneligiblePinsentryParams;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.mobile.MobilePinsentryIneligible;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical.NoEligibleCards;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.OneTimePasscodeIneligible;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.PinsentryFunction;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical.PhysicalPinsentry;
import uk.co.idv.domain.entities.verificationcontext.method.pushnotification.PushNotification;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResultsAlwaysEmpty;

import java.util.Collections;


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
        final VerificationMethod pushNotification = PushNotification.builder()
                .params(new IneligibleVerificationMethodParams())
                .eligibility(new NoMobileApplication())
                .results(new VerificationResultsAlwaysEmpty())
                .build();
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
        final VerificationMethod mobilePinsentry = new MobilePinsentryIneligible(PinsentryFunction.RESPOND);
        return new SingleMethodSequence(mobilePinsentry);
    }

    private static VerificationSequence buildOneTimePasscodeSmsSequence() {
        final VerificationMethod oneTimePasscodeSms = new OneTimePasscodeIneligible();
        return new SingleMethodSequence(oneTimePasscodeSms);
    }

}
