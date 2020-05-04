package uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode;

import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.OneTimePasscode.OneTimePasscodeBuilder;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.params.OneTimePasscodeParamsMother;

import java.util.Arrays;
import java.util.Collections;

public class OneTimePasscodeMother {

    public static OneTimePasscode eligible() {
        return eligible(DeliveryMethodMother.sms());
    }

    public static OneTimePasscode ineligible() {
        return OneTimePasscode.ineligibleBuilder()
                .params(OneTimePasscodeParamsMother.ineligible())
                .deliveryMethods(Collections.emptyList())
                .eligibility(new NoEligibleDeliveryMethods())
                .build();
    }

    public static OneTimePasscode eligible(final DeliveryMethod... deliveryMethods) {
        return OneTimePasscode.eligibleBuilder()
                .params(OneTimePasscodeParamsMother.eligible())
                .deliveryMethods(Arrays.asList(deliveryMethods))
                .build();
    }

    public static OneTimePasscodeBuilder eligibleBuilder() {
        return OneTimePasscode.eligibleBuilder()
                .deliveryMethods(DeliveryMethodMother.oneSms())
                .params(OneTimePasscodeParamsMother.eligible());
    }

}
