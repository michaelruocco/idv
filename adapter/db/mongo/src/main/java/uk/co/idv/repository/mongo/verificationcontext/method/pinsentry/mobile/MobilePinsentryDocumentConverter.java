package uk.co.idv.repository.mongo.verificationcontext.method.pinsentry.mobile;

import lombok.Builder;
import uk.co.idv.repository.mongo.verificationcontext.method.VerificationMethodDocumentConverter;
import uk.co.idv.repository.mongo.verificationcontext.method.VerificationMethodDocument;
import uk.co.idv.repository.mongo.verificationcontext.eligibility.EligibilityDocumentConverter;
import uk.co.idv.repository.mongo.verificationcontext.result.VerificationResultsDocumentConverter;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.MobilePinsentry;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.MobilePinsentryEligible;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.MobilePinsentryIneligible;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.VerificationMethod;

@Builder
public class MobilePinsentryDocumentConverter implements VerificationMethodDocumentConverter {

    private final VerificationResultsDocumentConverter resultsConverter;
    private final EligibilityDocumentConverter eligibilityConverter;

    @Override
    public boolean supportsMethod(final String methodName) {
        return MobilePinsentry.NAME.equals(methodName);
    }

    @Override
    public VerificationMethod toMethod(final VerificationMethodDocument methodDocument) {
        final MobilePinsentryDocument document = (MobilePinsentryDocument) methodDocument;
        if (document.isEligible()) {
            return toEligible(document);
        }
        return toIneligible(document);
    }

    private VerificationMethod toEligible(final MobilePinsentryDocument document) {
        return new MobilePinsentryEligible(
                document.getFunction(),
                resultsConverter.toResults(document.getResults())
        );
    }

    private VerificationMethod toIneligible(final MobilePinsentryDocument document) {
        return new MobilePinsentryIneligible(document.getFunction());
    }

    @Override
    public VerificationMethodDocument toDocument(final VerificationMethod method) {
        final MobilePinsentry mobilePinsentry = (MobilePinsentry) method;
        final MobilePinsentryDocument document = new MobilePinsentryDocument();
        VerificationMethodDocumentConverter.populateCommonFields(method, document);
        document.setFunction(mobilePinsentry.getFunction());
        document.setEligibility(eligibilityConverter.toDocument(mobilePinsentry.getEligibility()));
        document.setResults(resultsConverter.toDocuments(mobilePinsentry.getResults()));
        return document;
    }

}
