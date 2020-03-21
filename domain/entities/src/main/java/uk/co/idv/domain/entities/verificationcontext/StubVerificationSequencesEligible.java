package uk.co.idv.domain.entities.verificationcontext;

import lombok.extern.slf4j.Slf4j;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.DeliveryMethod;
import uk.co.idv.domain.entities.cardnumber.CardNumber;
import uk.co.idv.domain.entities.cardnumber.CreditCardNumber;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.DefaultPasscodeSettings;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.SmsDeliveryMethod;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.mobile.MobilePinsentryEligible;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.OneTimePasscodeEligible;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.PasscodeSettings;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical.PhysicalPinsentryEligible;
import uk.co.idv.domain.entities.verificationcontext.method.pushnotification.PushNotificationEligible;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.PinsentryFunction;

import java.util.Collection;
import java.util.Collections;

@Slf4j
public class StubVerificationSequencesEligible extends VerificationSequences {

    private static final VerificationSequence PUSH_AUTHENTICATION = buildPushNotificationSequence();
    private static final VerificationSequence PHYSICAL_PINSENTRY = buildPhysicalPinsentrySequence();
    private static final VerificationSequence MOBILE_PINSENTRY = buildMobilePinsentrySequence();
    private static final VerificationSequence ONE_TIME_PASSCODE = buildOneTimePasscodeSequence();

    public StubVerificationSequencesEligible() {
        super(PUSH_AUTHENTICATION,
                PHYSICAL_PINSENTRY,
                MOBILE_PINSENTRY,
                ONE_TIME_PASSCODE
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

    private static VerificationSequence buildOneTimePasscodeSequence() {
        final Collection<DeliveryMethod> deliveryMethods = Collections.singleton(new SmsDeliveryMethod(loadPhoneNumber()));
        final PasscodeSettings passcodeSettings = new DefaultPasscodeSettings();
        final VerificationMethod oneTimePasscode = new OneTimePasscodeEligible(passcodeSettings, deliveryMethods);
        return new SingleMethodSequence(oneTimePasscode);
    }

    private static String loadPhoneNumber() {
        final String phoneNumber = System.getProperty("stubbed.phone.number", "07809385580");
        log.info("loaded system property stubbed.phone.number {}", phoneNumber);
        return phoneNumber;
    }

}
