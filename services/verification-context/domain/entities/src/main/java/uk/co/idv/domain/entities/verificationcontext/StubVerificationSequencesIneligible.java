package uk.co.idv.domain.entities.verificationcontext;

import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.NoEligibleDeliveryMethods;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.OneTimePasscode;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.params.DefaultOneTimePasscodeParams;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.params.DefaultPasscodeSettings;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.params.OneTimePasscodeParams;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.params.PasscodeSettings;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.NoMobileApplication;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.params.IneligiblePinsentryParams;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.mobile.MobilePinsentry;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical.NoEligibleCards;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.params.PinsentryFunction;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical.PhysicalPinsentry;
import uk.co.idv.domain.entities.verificationcontext.method.pushnotification.PushNotificationIneligible;

import java.time.Duration;
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

    private static VerificationSequence buildOneTimePasscodeSmsSequence() {
        final VerificationMethod oneTimePasscodeSms = OneTimePasscode.ineligibleBuilder()
                .params(buildOneTimePasscodeParams())
                .eligibility(new NoEligibleDeliveryMethods())
                .deliveryMethods(Collections.emptyList())
                .build();
        return new SingleMethodSequence(oneTimePasscodeSms);
    }

    private static OneTimePasscodeParams buildOneTimePasscodeParams() {
        return DefaultOneTimePasscodeParams.builder()
                .maxAttempts(0)
                .duration(Duration.ZERO)
                .passcodeSettings(buildPasscodeSettings())
                .build();
    }

    private static PasscodeSettings buildPasscodeSettings() {
        return DefaultPasscodeSettings.builder()
                .length(0)
                .duration(Duration.ZERO)
                .maxDeliveries(0)
                .build();
    }

}
