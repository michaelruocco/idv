package uk.co.mruoc.idv.verificationcontext.domain.model;

import uk.co.mruoc.idv.verificationcontext.domain.model.method.CardNumber;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.CreditCardNumber;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.DefaultPasscodeSettings;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.MobileNumber;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.MobilePinsentryEligible;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.OneTimePasscodeSmsEligible;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.PasscodeSettings;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.PhysicalPinsentryEligible;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.PushNotificationEligible;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.VerificationMethod;

import java.util.Collection;
import java.util.Collections;

import static uk.co.mruoc.idv.verificationcontext.domain.model.method.PinsentryFunction.RESPOND;

public class FakeVerificationSequencesEligible extends VerificationSequences {

    private static final VerificationSequence PUSH_AUTHENTICATION = buildPushNotificationSequence();
    private static final VerificationSequence PHYSICAL_PINSENTRY = buildPhysicalPinsentrySequence();
    private static final VerificationSequence MOBILE_PINSENTRY = buildMobilePinsentrySequence();
    private static final VerificationSequence ONE_TIME_PASSCODE_SMS = buildOneTimePasscodeSmsSequence();

    public FakeVerificationSequencesEligible() {
        super(PUSH_AUTHENTICATION,
                PHYSICAL_PINSENTRY,
                MOBILE_PINSENTRY,
                ONE_TIME_PASSCODE_SMS
        );
    }

    private static VerificationSequence buildPushNotificationSequence() {
        final VerificationMethod pushNotification = new PushNotificationEligible();
        return new SingleMethodSequence(pushNotification);
    }

    private static VerificationSequence buildPhysicalPinsentrySequence() {
        final Collection<CardNumber> cardNumbers = Collections.singleton(new CreditCardNumber("4929991234567890"));
        final VerificationMethod physicalPinsentry = new PhysicalPinsentryEligible(RESPOND, cardNumbers);
        return new SingleMethodSequence(physicalPinsentry);
    }

    private static VerificationSequence buildMobilePinsentrySequence() {
        final VerificationMethod mobilePinsentry = new MobilePinsentryEligible(RESPOND);
        return new SingleMethodSequence(mobilePinsentry);
    }

    private static VerificationSequence buildOneTimePasscodeSmsSequence() {
        final Collection<MobileNumber> mobileNumbers = Collections.singleton(new MobileNumber("07809385580"));
        final PasscodeSettings passcodeSettings = new DefaultPasscodeSettings();
        final VerificationMethod oneTimePasscodeSms = new OneTimePasscodeSmsEligible(passcodeSettings, mobileNumbers);
        return new SingleMethodSequence(oneTimePasscodeSms);
    }

}
