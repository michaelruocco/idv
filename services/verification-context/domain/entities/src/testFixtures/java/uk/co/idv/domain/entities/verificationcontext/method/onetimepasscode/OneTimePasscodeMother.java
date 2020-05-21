package uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode;

import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.OneTimePasscodeEligible.OneTimePasscodeEligibleBuilder;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.params.OneTimePasscodeParamsMother;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResultsMother;

import java.util.Arrays;
import java.util.Collections;

public class OneTimePasscodeMother {

    public static OneTimePasscode ineligible() {
        return new OneTimePasscodeIneligible(
                new NoEligibleDeliveryMethods(),
                Collections.emptyList()
        );
    }

    public static OneTimePasscode eligible() {
        return eligibleBuilder().build();
    }

    public static OneTimePasscode eligible(final DeliveryMethod... deliveryMethods) {
        return eligibleBuilder()
                .deliveryMethods(Arrays.asList(deliveryMethods))
                .build();
    }

    public static OneTimePasscodeEligibleBuilder eligibleBuilder() {
        return OneTimePasscodeEligible.builder()
                .params(OneTimePasscodeParamsMother.eligible())
                .results(VerificationResultsMother.empty())
                .deliveryMethods(DeliveryMethodMother.oneSms());
    }

}
