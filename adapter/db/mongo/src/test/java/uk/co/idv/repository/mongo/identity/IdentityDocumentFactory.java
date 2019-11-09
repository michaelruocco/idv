package uk.co.idv.repository.mongo.identity;

import org.meanbean.lang.Factory;

public class IdentityDocumentFactory implements Factory<IdentityDocument> {

    @Override
    public IdentityDocument create() {
        return IdentityDocumentMother.build();
    }

}
