package uk.co.idv.domain.entities.verificationcontext.method.pushnotification;

import uk.co.idv.domain.entities.verificationcontext.method.DefaultVerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethodParams;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.Eligibility;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResults;

public class PushNotification extends DefaultVerificationMethod {

    public static final String NAME = "push-notification";

    public PushNotification(final VerificationMethodParams params,
                            final Eligibility eligibility,
                            final VerificationResults results) {
        super(NAME, params, eligibility, results);
    }

}
