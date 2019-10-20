package uk.co.mruoc.idv.mongo.verificationcontext.dao.method;

import lombok.Builder;
import uk.co.mruoc.idv.mongo.verificationcontext.dao.EligibilityConverter;
import uk.co.mruoc.idv.mongo.verificationcontext.dao.result.VerificationResultsConverter;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.PushNotification;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.PushNotificationEligible;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.PushNotificationIneligible;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.VerificationMethod;
import uk.co.mruoc.idv.verificationcontext.domain.model.result.VerificationResults;

@Builder
public class PushNotificationConverter implements VerificationMethodConverter {

    private final VerificationResultsConverter resultsConverter;
    private final EligibilityConverter eligibilityConverter;

    @Override
    public String getSupportedMethodName() {
        return PushNotification.NAME;
    }

    @Override
    public VerificationMethod toMethod(final VerificationMethodDocument document) {
        final VerificationResults results = resultsConverter.toResults(document.getResults());
        if (document.isEligible()) {
            return new PushNotificationEligible(results);
        }
        return new PushNotificationIneligible();
    }

    @Override
    public VerificationMethodDocument toDocument(final VerificationMethod method) {
        final VerificationMethodDocument document = new VerificationMethodDocument();
        VerificationMethodConverter.populateCommonFields(method, document);
        document.setEligibility(eligibilityConverter.toDocument(method.getEligibility()));
        document.setResults(resultsConverter.toDocuments(method.getResults()));
        return document;
    }

}
