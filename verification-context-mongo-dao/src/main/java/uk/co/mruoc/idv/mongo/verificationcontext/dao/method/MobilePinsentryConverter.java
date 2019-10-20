package uk.co.mruoc.idv.mongo.verificationcontext.dao.method;

import lombok.Builder;
import uk.co.mruoc.idv.mongo.verificationcontext.dao.EligibilityConverter;
import uk.co.mruoc.idv.mongo.verificationcontext.dao.result.VerificationResultConverter;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.MobilePinsentry;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.MobilePinsentryEligible;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.MobilePinsentryIneligible;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.VerificationMethod;

@Builder
public class MobilePinsentryConverter implements VerificationMethodConverter {

    private final VerificationResultConverter resultConverter;
    private final EligibilityConverter eligibilityConverter;

    @Override
    public String getSupportedMethodName() {
        return MobilePinsentry.NAME;
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
                resultConverter.toResults(document.getResults())
        );
    }

    private VerificationMethod toIneligible(final MobilePinsentryDocument document) {
        return new MobilePinsentryIneligible(document.getFunction());
    }

    @Override
    public VerificationMethodDocument toDocument(final VerificationMethod method) {
        final MobilePinsentry mobilePinsentry = (MobilePinsentry) method;
        final MobilePinsentryDocument document = new MobilePinsentryDocument();
        VerificationMethodConverter.populateCommonFields(method, document);
        document.setFunction(mobilePinsentry.getFunction());
        document.setEligibility(eligibilityConverter.toDocument(mobilePinsentry.getEligibility()));
        document.setResults(resultConverter.toDocuments(mobilePinsentry.getResults()));
        return document;
    }

}
