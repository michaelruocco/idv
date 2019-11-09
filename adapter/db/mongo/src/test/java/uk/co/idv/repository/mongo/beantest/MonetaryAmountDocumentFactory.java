package uk.co.idv.repository.mongo.beantest;

import org.meanbean.lang.Factory;
import uk.co.idv.repository.mongo.activity.MonetaryAmountDocument;
import uk.co.idv.repository.mongo.activity.MonetaryAmountDocumentMother;

public class MonetaryAmountDocumentFactory implements Factory<MonetaryAmountDocument> {

    @Override
    public MonetaryAmountDocument create() {
        return MonetaryAmountDocumentMother.build();
    }

}
