package uk.co.mruoc.idv.mongo.verificationcontext.dao.method;

import lombok.Builder;
import uk.co.mruoc.idv.mongo.verificationcontext.dao.eligibility.EligibilityDocumentConverter;
import uk.co.mruoc.idv.mongo.verificationcontext.dao.result.VerificationResultsDocumentConverter;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.CardCredentials;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.CardCredentialsEligible;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.CardCredentialsIneligible;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.VerificationMethod;
import uk.co.mruoc.idv.verificationcontext.domain.model.result.VerificationResults;

@Builder
public class CardCredentialsDocumentConverter implements VerificationMethodDocumentConverter {

    private final VerificationResultsDocumentConverter resultsConverter;
    private final EligibilityDocumentConverter eligibilityConverter;

    @Override
    public boolean supportsMethod(final String methodName) {
        return CardCredentials.NAME.equals(methodName);
    }

    @Override
    public VerificationMethod toMethod(final VerificationMethodDocument document) {
        final VerificationResults results = resultsConverter.toResults(document.getResults());
        if (document.isEligible()) {
            return new CardCredentialsEligible(results);
        }
        return new CardCredentialsIneligible();
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
