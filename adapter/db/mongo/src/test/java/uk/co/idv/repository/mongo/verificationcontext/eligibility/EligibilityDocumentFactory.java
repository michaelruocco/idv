package uk.co.idv.repository.mongo.verificationcontext.eligibility;

import org.meanbean.lang.Factory;

public class EligibilityDocumentFactory implements Factory<EligibilityDocument> {

    @Override
    public EligibilityDocument create() {
        return EligibilityDocumentMother.eligible();
    }

}
