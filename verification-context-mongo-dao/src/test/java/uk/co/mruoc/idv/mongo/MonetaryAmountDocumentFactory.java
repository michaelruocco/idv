package uk.co.mruoc.idv.mongo;

import org.meanbean.lang.Factory;
import uk.co.mruoc.idv.mongo.dao.activity.MonetaryAmountDocument;
import uk.co.mruoc.idv.mongo.dao.activity.MonetaryAmountDocumentMother;

public class MonetaryAmountDocumentFactory implements Factory<MonetaryAmountDocument> {

    @Override
    public MonetaryAmountDocument create() {
        return MonetaryAmountDocumentMother.build();
    }

}
