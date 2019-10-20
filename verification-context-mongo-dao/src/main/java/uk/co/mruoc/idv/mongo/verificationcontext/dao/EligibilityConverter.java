package uk.co.mruoc.idv.mongo.verificationcontext.dao;

import uk.co.mruoc.idv.verificationcontext.domain.model.method.Eligibility;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.Eligible;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.Ineligible;

public class EligibilityConverter {

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
