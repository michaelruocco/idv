package uk.co.mruoc.idv.mongo.beantest;

import org.meanbean.lang.Factory;
import uk.co.mruoc.idv.mongo.identity.dao.IdentityDocument;
import uk.co.mruoc.idv.mongo.identity.dao.IdentityDocumentMother;

public class IdentityDocumentFactory implements Factory<IdentityDocument> {

    @Override
    public IdentityDocument create() {
        return IdentityDocumentMother.build();
    }

}
