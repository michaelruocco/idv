package uk.co.idv.domain.entities.verificationcontext.method.pushnotification;

import uk.co.idv.domain.entities.verificationcontext.method.eligibility.Ineligible;
import uk.co.idv.domain.entities.verificationcontext.method.params.IneligibleVerificationMethodParams;
import uk.co.idv.domain.entities.verificationcontext.result.DefaultVerificationResults;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResults;

public class PushNotificationIneligible extends PushNotification {

    public PushNotificationIneligible(final Ineligible reason) {
        this(reason, new DefaultVerificationResults());
    }

    public PushNotificationIneligible(final Ineligible reason,
                                      final VerificationResults results) {
        super(new IneligibleVerificationMethodParams(), reason, results);
    }

}
