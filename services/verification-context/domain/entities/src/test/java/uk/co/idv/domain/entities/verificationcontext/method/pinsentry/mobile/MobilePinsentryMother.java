package uk.co.idv.domain.entities.verificationcontext.method.pinsentry.mobile;

import uk.co.idv.domain.entities.verificationcontext.method.eligibility.Eligible;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.NoMobileApplication;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.IneligiblePinsentryParams;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.PinsentryFunction;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.PinsentryParams;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.PinsentryParams.PinsentryParamsBuilder;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.mobile.MobilePinsentry.MobilePinsentryBuilder;
import uk.co.idv.domain.entities.verificationcontext.result.DefaultVerificationResults;

import java.time.Duration;

public class MobilePinsentryMother {

    private static final PinsentryFunction FUNCTION = PinsentryFunction.RESPOND;

    public static MobilePinsentry ineligible() {
        return ineligible(FUNCTION);
    }

    public static MobilePinsentry ineligible(final PinsentryFunction function) {
        return MobilePinsentry.ineligibleBuilder()
                .params(new IneligiblePinsentryParams(function))
                .eligibility(new NoMobileApplication())
                .build();
    }

    public static MobilePinsentry eligible() {
        return eligibleBuilder().build();
    }

    public static MobilePinsentryBuilder eligibleBuilder() {
        return MobilePinsentry.eligibleBuilder()
                .params(paramsBuilder().build())
                .eligibility(new Eligible())
                .results(new DefaultVerificationResults());
    }

    public static PinsentryParams paramsWithMaxAttempts(int maxAttempts) {
        return paramsBuilder()
                .maxAttempts(maxAttempts)
                .build();
    }

    public static PinsentryParamsBuilder paramsBuilder() {
        return PinsentryParams.builder()
                .maxAttempts(1)
                .duration(Duration.ofMinutes(5))
                .function(FUNCTION);
    }

}
