package uk.co.idv.domain.entities.verification.onetimepasscode;

import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.DeliveryMethodMother;

import java.util.Collection;
import java.util.Collections;

public class OneTimePasscodeGenerationMother {

    private static final String DEFAULT_PASSCODE = "12345678";

    public static Collection<OneTimePasscodeGeneration> oneSmsGeneration(final String passcode) {
        return Collections.singleton(smsGeneration(passcode));
    }

    public static OneTimePasscodeGeneration smsGeneration() {
        return smsGeneration(DEFAULT_PASSCODE);
    }

    public static OneTimePasscodeGeneration smsGeneration(final String passcode) {
        return OneTimePasscodeGeneration.builder()
                .deliveryMethod(DeliveryMethodMother.sms())
                .passcode(passcode)
                .build();
    }

}
