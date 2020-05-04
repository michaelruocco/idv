package uk.co.idv.domain.entities.verificationcontext.method.pinsentry.mobile;

import uk.co.idv.domain.entities.verificationcontext.method.eligibility.Eligible;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.NoMobileApplication;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.params.DefaultPinsentryParams;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.params.DefaultPinsentryParams.DefaultPinsentryParamsBuilder;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.params.IneligiblePinsentryParams;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.params.PinsentryFunction;
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

    public static DefaultPinsentryParams paramsWithMaxAttempts(int maxAttempts) {
        return paramsBuilder()
                .maxAttempts(maxAttempts)
                .build();
    }

    public static DefaultPinsentryParamsBuilder paramsBuilder() {
        return DefaultPinsentryParams.builder()
                .maxAttempts(1)
                .duration(Duration.ofMinutes(5))
                .function(FUNCTION);
    }

}
