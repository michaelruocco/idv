package uk.co.mruoc.idv.mongo.verificationcontext.dao;

import lombok.Builder;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.PushNotification;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.PushNotificationEligible;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.PushNotificationIneligible;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.VerificationMethod;
import uk.co.mruoc.idv.verificationcontext.domain.model.result.VerificationResults;

import java.util.Collections;

@Builder
public class PushNotificationConverter implements VerificationMethodConverter {

    private final VerificationResultConverter resultConverter;
    private final EligibilityConverter eligibilityConverter;

    @Override
    public String getSupportedMethodName() {
        return PushNotification.NAME;
    }

    @Override
    public VerificationMethod toMethod(final VerificationMethodDocument document) {
        final VerificationResults results = resultConverter.toResults(document.getResults());
        if (document.isEligible()) {
            return new PushNotificationEligible(results);
        }
        return new PushNotificationIneligible();
    }

    @Override
    public VerificationMethodDocument toDocument(final VerificationMethod method) {
        return VerificationMethodDocument.builder()
                .name(method.getName())
                .maxAttempts(method.getMaxAttempts())
                .duration(method.getDuration().toString())
                .eligibility(eligibilityConverter.toDocument(method.getEligibility()))
                .results(resultConverter.toDocuments(method.getResults()))
                .properties(Collections.emptyMap())
                .build();
    }
}
