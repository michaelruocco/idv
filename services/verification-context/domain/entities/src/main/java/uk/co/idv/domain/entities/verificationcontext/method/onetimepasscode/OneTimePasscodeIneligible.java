package uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode;

import uk.co.idv.domain.entities.verificationcontext.method.eligibility.Ineligible;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.params.IneligibleOneTimePasscodeParams;
import uk.co.idv.domain.entities.verificationcontext.result.DefaultVerificationResults;

import java.util.Collection;

public class OneTimePasscodeIneligible extends OneTimePasscode {

    public OneTimePasscodeIneligible(final Ineligible reason,
                                     final Collection<DeliveryMethod> deliveryMethods) {
        super(
                new IneligibleOneTimePasscodeParams(),
                reason,
                new DefaultVerificationResults(),
                deliveryMethods
        );
    }

}
