package uk.co.mruoc.idv.mongo;

import org.meanbean.lang.Factory;
import uk.co.mruoc.idv.mongo.identity.dao.AliasDocument;
import uk.co.mruoc.idv.mongo.identity.dao.AliasDocumentMother;

public class AliasDocumentFactory implements Factory<AliasDocument> {

    @Override
    public AliasDocument create() {
        return AliasDocumentMother.idvId();
    }

}
