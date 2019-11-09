package uk.co.idv.repository.mongo.identity.alias;

import org.meanbean.lang.Factory;

public class AliasDocumentFactory implements Factory<AliasDocument> {

    @Override
    public AliasDocument create() {
        return AliasDocumentMother.idvId();
    }

}
