package uk.co.idv.repository.mongo.verificationcontext.method.pinsentry.physical;

import lombok.Builder;
import uk.co.idv.repository.mongo.verificationcontext.eligibility.EligibilityDocumentConverter;
import uk.co.idv.repository.mongo.verificationcontext.method.VerificationMethodDocument;
import uk.co.idv.repository.mongo.verificationcontext.method.VerificationMethodDocumentConverter;
import uk.co.idv.repository.mongo.verificationcontext.result.VerificationResultsDocumentConverter;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical.PhysicalPinsentry;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical.PhysicalPinsentryEligible;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical.PhysicalPinsentryIneligible;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;

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
