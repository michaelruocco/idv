package uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode;

import lombok.Builder;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.Eligible;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.params.OneTimePasscodeParams;
import uk.co.idv.domain.entities.verificationcontext.result.DefaultVerificationResults;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResults;

import java.util.Collection;

public class OneTimePasscodeEligible extends OneTimePasscode {

    public OneTimePasscodeEligible(final OneTimePasscodeParams params,
                                   final Collection<DeliveryMethod> deliveryMethods) {
        this(params, new DefaultVerificationResults(), deliveryMethods);
    }

    @Builder
    public OneTimePasscodeEligible(final OneTimePasscodeParams params,
                                   final VerificationResults results,
                                   final Collection<DeliveryMethod> deliveryMethods) {
        super(params, new Eligible(), results, deliveryMethods);
    }

}
