package uk.co.mruoc.idv.mongo.beantest;

import org.meanbean.lang.Factory;
import uk.co.mruoc.idv.mongo.verificationcontext.dao.eligibility.EligibilityDocument;
import uk.co.mruoc.idv.mongo.verificationcontext.dao.eligibility.EligibilityDocumentMother;

public class EligibilityDocumentFactory implements Factory<EligibilityDocument> {

    @Override
    public EligibilityDocument create() {
        return EligibilityDocumentMother.eligible();
    }

}
