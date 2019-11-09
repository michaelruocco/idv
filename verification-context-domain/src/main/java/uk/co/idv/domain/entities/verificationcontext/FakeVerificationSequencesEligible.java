package uk.co.idv.domain.entities.verificationcontext;

import uk.co.idv.domain.entities.verificationcontext.method.CardNumber;
import uk.co.idv.domain.entities.verificationcontext.method.CreditCardNumber;
import uk.co.idv.domain.entities.verificationcontext.method.DefaultPasscodeSettings;
import uk.co.idv.domain.entities.verificationcontext.method.MobileNumber;
import uk.co.idv.domain.entities.verificationcontext.method.MobilePinsentryEligible;
import uk.co.idv.domain.entities.verificationcontext.method.OneTimePasscodeSmsEligible;
import uk.co.idv.domain.entities.verificationcontext.method.PasscodeSettings;
import uk.co.idv.domain.entities.verificationcontext.method.PhysicalPinsentryEligible;
import uk.co.idv.domain.entities.verificationcontext.method.PushNotificationEligible;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.method.PinsentryFunction;

import java.util.Collection;
import java.util.Collections;


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
        final VerificationMethod physicalPinsentry = new PhysicalPinsentryEligible(PinsentryFunction.RESPOND, cardNumbers);
        return new SingleMethodSequence(physicalPinsentry);
    }

    private static VerificationSequence buildMobilePinsentrySequence() {
        final VerificationMethod mobilePinsentry = new MobilePinsentryEligible(PinsentryFunction.RESPOND);
        return new SingleMethodSequence(mobilePinsentry);
    }

    private static VerificationSequence buildOneTimePasscodeSmsSequence() {
        final Collection<MobileNumber> mobileNumbers = Collections.singleton(new MobileNumber("07809385580"));
        final PasscodeSettings passcodeSettings = new DefaultPasscodeSettings();
        final VerificationMethod oneTimePasscodeSms = new OneTimePasscodeSmsEligible(passcodeSettings, mobileNumbers);
        return new SingleMethodSequence(oneTimePasscodeSms);
    }

}
