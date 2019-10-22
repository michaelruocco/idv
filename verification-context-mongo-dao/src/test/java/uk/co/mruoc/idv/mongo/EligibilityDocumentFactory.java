package uk.co.mruoc.idv.mongo;

import org.meanbean.lang.Factory;
import uk.co.mruoc.idv.mongo.verificationcontext.dao.EligibilityDocument;
import uk.co.mruoc.idv.mongo.verificationcontext.dao.EligibilityDocumentMother;

public class EligibilityDocumentFactory implements Factory<EligibilityDocument> {

    @Override
    public EligibilityDocument create() {
        return EligibilityDocumentMother.eligible();
    }

}
