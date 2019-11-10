package uk.co.idv.repository.mongo.verificationcontext.method.pushnotification;

import lombok.Builder;
import uk.co.idv.repository.mongo.verificationcontext.eligibility.EligibilityDocumentConverter;
import uk.co.idv.repository.mongo.verificationcontext.method.VerificationMethodDocument;
import uk.co.idv.repository.mongo.verificationcontext.method.VerificationMethodDocumentConverter;
import uk.co.idv.repository.mongo.verificationcontext.result.VerificationResultsDocumentConverter;
import uk.co.idv.domain.entities.verificationcontext.method.pushnotification.PushNotification;
import uk.co.idv.domain.entities.verificationcontext.method.pushnotification.PushNotificationEligible;
import uk.co.idv.domain.entities.verificationcontext.method.pushnotification.PushNotificationIneligible;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResults;

@Builder
public class PushNotificationDocumentConverter implements VerificationMethodDocumentConverter {

    private final VerificationResultsDocumentConverter resultsConverter;
    private final EligibilityDocumentConverter eligibilityConverter;

    @Override
    public boolean supportsMethod(final String methodName) {
        return PushNotification.NAME.equals(methodName);
    }

    @Override
    public VerificationMethod toMethod(final VerificationMethodDocument document) {
        if (document.isEligible()) {
            final VerificationResults results = resultsConverter.toResults(document.getResults());
            return new PushNotificationEligible(results);
        }
        return new PushNotificationIneligible();
    }

    @Override
    public VerificationMethodDocument toDocument(final VerificationMethod method) {
        final VerificationMethodDocument document = new VerificationMethodDocument();
        VerificationMethodDocumentConverter.populateCommonFields(method, document);
        document.setEligibility(eligibilityConverter.toDocument(method.getEligibility()));
        document.setResults(resultsConverter.toDocuments(method.getResults()));
        return document;
    }

}
