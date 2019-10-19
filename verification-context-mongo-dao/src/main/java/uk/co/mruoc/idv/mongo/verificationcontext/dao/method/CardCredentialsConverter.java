package uk.co.mruoc.idv.mongo.verificationcontext.dao.method;

import lombok.Builder;
import uk.co.mruoc.idv.mongo.verificationcontext.dao.EligibilityConverter;
import uk.co.mruoc.idv.mongo.verificationcontext.dao.result.VerificationResultConverter;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.CardCredentials;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.CardCredentialsEligible;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.CardCredentialsIneligible;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.VerificationMethod;
import uk.co.mruoc.idv.verificationcontext.domain.model.result.VerificationResults;

@Builder
public class CardCredentialsConverter implements VerificationMethodConverter {

    private final VerificationResultConverter resultConverter;
    private final EligibilityConverter eligibilityConverter;

    @Override
    public String getSupportedMethodName() {
        return CardCredentials.NAME;
    }

    @Override
    public VerificationMethod toMethod(final VerificationMethodDocument document) {
        final VerificationResults results = resultConverter.toResults(document.getResults());
        if (document.isEligible()) {
            return new CardCredentialsEligible(results);
        }
        return new CardCredentialsIneligible();
    }

    @Override
    public VerificationMethodDocument toDocument(final VerificationMethod method) {
        final VerificationMethodDocument document = new VerificationMethodDocument();
        document.setName(method.getName());
        document.setMaxAttempts(method.getMaxAttempts());
        document.setDuration(method.getDuration().toString());
        document.setEligibility(eligibilityConverter.toDocument(method.getEligibility()));
        document.setResults(resultConverter.toDocuments(method.getResults()));
        return document;
    }

}
