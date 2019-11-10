package uk.co.idv.repository.mongo.verificationcontext.method.cardcredentials;

import lombok.Builder;
import uk.co.idv.repository.mongo.verificationcontext.eligibility.EligibilityDocumentConverter;
import uk.co.idv.repository.mongo.verificationcontext.method.VerificationMethodDocument;
import uk.co.idv.repository.mongo.verificationcontext.method.VerificationMethodDocumentConverter;
import uk.co.idv.repository.mongo.verificationcontext.result.VerificationResultsDocumentConverter;
import uk.co.idv.domain.entities.verificationcontext.method.cardcredentials.CardCredentials;
import uk.co.idv.domain.entities.verificationcontext.method.cardcredentials.CardCredentialsEligible;
import uk.co.idv.domain.entities.verificationcontext.method.cardcredentials.CardCredentialsIneligible;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResults;

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
