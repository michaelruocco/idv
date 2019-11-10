package uk.co.idv.repository.mongo.verificationcontext.eligibility;


public class EligibilityDocumentMother {

    public static EligibilityDocument ineligible() {
        final EligibilityDocument document = new EligibilityDocument();
        document.setEligible(false);
        document.setReason("fake reason");
        return document;
    }

    public static EligibilityDocument eligible() {
        final EligibilityDocument document = new EligibilityDocument();
        document.setEligible(true);
        return document;
    }

}