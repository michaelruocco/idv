package uk.co.idv.repository.mongo.verificationcontext.eligibility;

import uk.co.idv.domain.entities.verificationcontext.method.eligibility.Eligibility;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.Eligible;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.Ineligible;

public class EligibilityDocumentConverter {

    public Eligibility toEligibility(final EligibilityDocument document) {
        if (document.isEligible()) {
            return new Eligible();
        }
        return toIneligible(document);
    }

    public Ineligible toIneligible(final EligibilityDocument document) {
        return new Ineligible(document.getReason());
    }

    public EligibilityDocument toDocument(final Eligibility eligibility) {
        final EligibilityDocument document = new EligibilityDocument();
        document.setEligible(eligibility.isEligible());
        eligibility.getReason().ifPresent(document::setReason);
        return document;
    }

}
