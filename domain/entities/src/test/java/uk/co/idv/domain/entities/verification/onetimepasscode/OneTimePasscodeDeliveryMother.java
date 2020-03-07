package uk.co.idv.domain.entities.verification.onetimepasscode;

import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.DeliveryMethodMother;

import java.util.Collection;
import java.util.Collections;

public class OneTimePasscodeDeliveryMother {

    private static final String DEFAULT_PASSCODE = "12345678";

    public static Collection<OneTimePasscodeDelivery> oneSmsDelivery(final String passcode) {
        return Collections.singleton(smsDelivery(passcode));
    }

    public static OneTimePasscodeDelivery smsDelivery() {
        return smsDelivery(DEFAULT_PASSCODE);
    }

    public static OneTimePasscodeDelivery smsDelivery(final String passcode) {
        return OneTimePasscodeDelivery.builder()
                .method(DeliveryMethodMother.sms())
                .passcode(passcode)
                .build();
    }

}
