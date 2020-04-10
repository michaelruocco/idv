package uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode;

import java.util.Arrays;

public class OneTimePasscodeMother {

    public static OneTimePasscodeEligible eligible() {
        return eligible(DeliveryMethodMother.sms());
    }

    public static OneTimePasscodeEligible eligible(final DeliveryMethod... deliveryMethods) {
        return new OneTimePasscodeEligible(new DefaultPasscodeSettings(), Arrays.asList(deliveryMethods));
    }

}
