package uk.co.idv.domain.entities.verificationcontext;

import lombok.extern.slf4j.Slf4j;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.DeliveryMethod;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.OneTimePasscodeEligible;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.params.DefaultOneTimePasscodeParams;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.params.OneTimePasscodeParams;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.params.PasscodeSettings;
import uk.co.idv.domain.entities.verificationcontext.method.params.DefaultVerificationMethodParams;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethodParams;
import uk.co.idv.domain.entities.card.number.CardNumber;
import uk.co.idv.domain.entities.card.number.CreditCardNumber;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.params.DefaultPasscodeSettings;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.SmsDeliveryMethod;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.params.DefaultPinsentryParams;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.mobile.MobilePinsentry;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical.PhysicalPinsentry;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.params.PinsentryFunction;
import uk.co.idv.domain.entities.verificationcontext.method.pushnotification.PushNotificationEligible;

import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

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
        final VerificationMethodParams params = buildPushNotificationParams();
        final VerificationMethod pushNotification = new PushNotificationEligible(params);
        return new SingleMethodSequence(pushNotification);
    }

    private static VerificationMethodParams buildPushNotificationParams() {
        return DefaultVerificationMethodParams.builder()
                .maxAttempts(5)
                .duration(Duration.ofMinutes(5))
                .build();
    }

    private static VerificationSequence buildPhysicalPinsentrySequence() {
        final Collection<CardNumber> cardNumbers = Collections.singleton(new CreditCardNumber("4929991234567890"));
        final VerificationMethod physicalPinsentry = PhysicalPinsentry.eligibleBuilder()
                .params(buildPinsentryParams())
                .cardNumbers(cardNumbers)
                .build();
        return new SingleMethodSequence(physicalPinsentry);
    }

    private static VerificationSequence buildMobilePinsentrySequence() {
        final VerificationMethod mobilePinsentry = MobilePinsentry.eligibleBuilder()
                .params(buildPinsentryParams())
                .build();
        return new SingleMethodSequence(mobilePinsentry);
    }

    private static DefaultPinsentryParams buildPinsentryParams() {
        return DefaultPinsentryParams.builder()
                .maxAttempts(1)
                .duration(Duration.ofMinutes(5))
                .function(PinsentryFunction.RESPOND)
                .build();
    }

    private static VerificationSequence buildOneTimePasscodeSequence() {
        final DeliveryMethod deliveryMethod = new SmsDeliveryMethod(UUID.randomUUID(), loadPhoneNumber());
        final VerificationMethod oneTimePasscode = new OneTimePasscodeEligible(
                buildOneTimePasscodeParams(),
                Collections.singleton(deliveryMethod)
        );
        return new SingleMethodSequence(oneTimePasscode);
    }

    private static String loadPhoneNumber() {
        final String phoneNumber = System.getProperty("stubbed.phone.number", "07809386681");
        log.info("loaded system property stubbed.phone.number {}", phoneNumber);
        return phoneNumber;
    }

    private static OneTimePasscodeParams buildOneTimePasscodeParams() {
        return DefaultOneTimePasscodeParams.builder()
                .maxAttempts(1)
                .duration(Duration.ofMinutes(5))
                .passcodeSettings(buildPasscodeSettings())
                .build();
    }

    private static PasscodeSettings buildPasscodeSettings() {
        return DefaultPasscodeSettings.builder()
                .length(8)
                .duration(Duration.ofMillis(150000))
                .maxDeliveries(3)
                .build();
    }

}
