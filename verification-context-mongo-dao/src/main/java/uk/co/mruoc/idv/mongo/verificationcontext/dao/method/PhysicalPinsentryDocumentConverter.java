package uk.co.mruoc.idv.mongo.verificationcontext.dao.method;

import lombok.Builder;
import uk.co.mruoc.idv.mongo.verificationcontext.dao.eligibility.EligibilityDocumentConverter;
import uk.co.mruoc.idv.mongo.verificationcontext.dao.result.VerificationResultsDocumentConverter;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.PhysicalPinsentry;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.PhysicalPinsentryEligible;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.PhysicalPinsentryIneligible;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.VerificationMethod;

import java.util.Collection;
import java.util.Collections;

@Builder
public class PhysicalPinsentryDocumentConverter implements VerificationMethodDocumentConverter {

    private final VerificationResultsDocumentConverter resultsConverter;
    private final EligibilityDocumentConverter eligibilityConverter;
    private final CardNumbersDocumentConverter cardNumbersConverter;

    @Override
    public boolean supportsMethod(final String methodName) {
        return PhysicalPinsentry.NAME.equals(methodName);
    }

    @Override
    public VerificationMethod toMethod(final VerificationMethodDocument methodDocument) {
        final PhysicalPinsentryDocument document = (PhysicalPinsentryDocument) methodDocument;
        if (document.isEligible()) {
            return toEligible(document);
        }
        return toIneligible(document);
    }

    private VerificationMethod toEligible(final PhysicalPinsentryDocument document) {
        return new PhysicalPinsentryEligible(
                document.getFunction(),
                cardNumbersConverter.toCardNumbers(document.getCardNumbers()),
                resultsConverter.toResults(document.getResults())
        );
    }

    private VerificationMethod toIneligible(final PhysicalPinsentryDocument document) {
        return new PhysicalPinsentryIneligible(
                eligibilityConverter.toIneligible(document.getEligibility()),
                document.getFunction()
        );
    }

    @Override
    public VerificationMethodDocument toDocument(final VerificationMethod method) {
        final PhysicalPinsentry physicalPinsentry = (PhysicalPinsentry) method;
        final PhysicalPinsentryDocument document = new PhysicalPinsentryDocument();
        VerificationMethodDocumentConverter.populateCommonFields(method, document);
        document.setFunction(physicalPinsentry.getFunction());
        document.setEligibility(eligibilityConverter.toDocument(physicalPinsentry.getEligibility()));
        document.setResults(resultsConverter.toDocuments(physicalPinsentry.getResults()));
        document.setCardNumbers(extractCardNumbers(physicalPinsentry));
        return document;
    }

    private Collection<CardNumberDocument> extractCardNumbers(final PhysicalPinsentry physicalPinsentry) {
        if (physicalPinsentry.isEligible()) {
            final PhysicalPinsentryEligible eligible = (PhysicalPinsentryEligible) physicalPinsentry;
            return cardNumbersConverter.toDocuments(eligible.getCardNumbers());
        }
        return Collections.emptyList();
    }

}
