package uk.co.idv.domain.entities.verificationcontext.method.pushnotification;

import lombok.Builder;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethodParams;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.Eligible;
import uk.co.idv.domain.entities.verificationcontext.result.DefaultVerificationResults;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResults;

public class PushNotificationEligible extends PushNotification {

    public PushNotificationEligible(final VerificationMethodParams params) {
        this(params, new DefaultVerificationResults());
    }

    @Builder
    public PushNotificationEligible(final VerificationMethodParams params,
                                    final VerificationResults results) {
        super(params, new Eligible(), results);
    }

}
