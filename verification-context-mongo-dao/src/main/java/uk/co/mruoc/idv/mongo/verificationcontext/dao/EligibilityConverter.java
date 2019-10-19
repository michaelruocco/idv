package uk.co.mruoc.idv.mongo.verificationcontext.dao;

import uk.co.mruoc.idv.verificationcontext.domain.model.method.Eligibility;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.Ineligible;

public class EligibilityConverter {

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
