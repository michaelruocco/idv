package uk.co.mruoc.idv.verificationcontext.domain.model;

import uk.co.mruoc.idv.verificationcontext.domain.model.method.DefaultPasscodeSettings;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.MobilePinsentry;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.NoEligibleCardNumbers;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.NoMobileApplication;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.OneTimePasscodeSmsIneligible;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.PasscodeSettings;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.PhysicalPinsentryIneligible;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.PushNotification;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.VerificationMethod;

import static uk.co.mruoc.idv.verificationcontext.domain.model.method.PinsentryFunction.RESPOND;

public class FakeVerificationSequencesIneligible extends VerificationSequences {

    private static final VerificationSequence PUSH_AUTHENTICATION = buildPushNotificationSequence();
    private static final VerificationSequence PHYSICAL_PINSENTRY = buildPhysicalPinsentrySequence();
    private static final VerificationSequence MOBILE_PINSENTRY = buildMobilePinsentrySequence();
    private static final VerificationSequence ONE_TIME_PASSCODE_SMS = buildOneTimePasscodeSmsSequence();

    public FakeVerificationSequencesIneligible() {
        super(PUSH_AUTHENTICATION,
                PHYSICAL_PINSENTRY,
                MOBILE_PINSENTRY,
                ONE_TIME_PASSCODE_SMS
        );
    }

    private static VerificationSequence buildPushNotificationSequence() {
        final VerificationMethod pushNotification = new PushNotification(new NoMobileApplication());
        return new SingleMethodSequence(pushNotification);
    }

    private static VerificationSequence buildPhysicalPinsentrySequence() {
        final VerificationMethod physicalPinsentry = new PhysicalPinsentryIneligible(new NoEligibleCardNumbers(), RESPOND);
        return new SingleMethodSequence(physicalPinsentry);
    }

    private static VerificationSequence buildMobilePinsentrySequence() {
        final VerificationMethod mobilePinsentry = new MobilePinsentry(new NoMobileApplication(), RESPOND);
        return new SingleMethodSequence(mobilePinsentry);
    }

    private static VerificationSequence buildOneTimePasscodeSmsSequence() {
        final PasscodeSettings passcodeSettings = new DefaultPasscodeSettings();
        final VerificationMethod oneTimePasscodeSms = new OneTimePasscodeSmsIneligible(passcodeSettings);
        return new SingleMethodSequence(oneTimePasscodeSms);
    }

}
