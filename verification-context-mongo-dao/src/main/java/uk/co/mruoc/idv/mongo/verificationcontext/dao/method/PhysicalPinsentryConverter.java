package uk.co.mruoc.idv.mongo.verificationcontext.dao.method;

import lombok.Builder;
import uk.co.mruoc.idv.mongo.verificationcontext.dao.EligibilityConverter;
import uk.co.mruoc.idv.mongo.verificationcontext.dao.result.VerificationResultsConverter;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.PhysicalPinsentry;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.PhysicalPinsentryEligible;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.PhysicalPinsentryIneligible;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.VerificationMethod;

import java.util.Collection;
import java.util.Collections;

@Builder
public class PhysicalPinsentryConverter implements VerificationMethodConverter {

    private final VerificationResultsConverter resultsConverter;
    private final EligibilityConverter eligibilityConverter;
    private final CardNumberConverter cardNumberConverter;

    @Override
    public String getSupportedMethodName() {
        return PhysicalPinsentry.NAME;
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
                cardNumberConverter.toCardNumbers(document.getCardNumbers()),
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
        VerificationMethodConverter.populateCommonFields(method, document);
        document.setFunction(physicalPinsentry.getFunction());
        document.setEligibility(eligibilityConverter.toDocument(physicalPinsentry.getEligibility()));
        document.setResults(resultsConverter.toDocuments(physicalPinsentry.getResults()));
        document.setCardNumbers(extractCardNumbers(physicalPinsentry));
        return document;
    }

    private Collection<CardNumberDocument> extractCardNumbers(final PhysicalPinsentry physicalPinsentry) {
        if (physicalPinsentry.isEligible()) {
            final PhysicalPinsentryEligible eligible = (PhysicalPinsentryEligible) physicalPinsentry;
            return cardNumberConverter.toDocuments(eligible.getCardNumbers());
        }
        return Collections.emptyList();
    }

}
