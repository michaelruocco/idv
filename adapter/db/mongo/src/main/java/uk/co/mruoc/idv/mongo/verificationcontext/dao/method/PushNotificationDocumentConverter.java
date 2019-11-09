package uk.co.mruoc.idv.mongo.verificationcontext.dao.method;

import lombok.Builder;
import uk.co.mruoc.idv.mongo.verificationcontext.dao.eligibility.EligibilityDocumentConverter;
import uk.co.mruoc.idv.mongo.verificationcontext.dao.result.VerificationResultsDocumentConverter;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.PushNotification;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.PushNotificationEligible;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.PushNotificationIneligible;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.VerificationMethod;
import uk.co.mruoc.idv.verificationcontext.domain.model.result.VerificationResults;

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
