package uk.co.idv.uk.repository.mongo.activity;

import uk.co.idv.repository.mongo.activity.ActivityDocumentConverterDelegator;
import uk.co.idv.repository.mongo.activity.MonetaryAmountDocumentConverter;
import uk.co.idv.repository.mongo.activity.OnlinePurchaseDocumentConverter;
import uk.co.idv.repository.mongo.activity.SimpleActivityDocumentConverter;

public class UkActivityDocumentConverterDelegator extends ActivityDocumentConverterDelegator {

    public UkActivityDocumentConverterDelegator() {
        super(
                new OnlinePurchaseDocumentConverter(new MonetaryAmountDocumentConverter()),
                new SimpleActivityDocumentConverter()
        );
    }

}
