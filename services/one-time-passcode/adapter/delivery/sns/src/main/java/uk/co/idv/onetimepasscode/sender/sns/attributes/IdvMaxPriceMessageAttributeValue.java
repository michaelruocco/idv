package uk.co.idv.onetimepasscode.sender.sns.attributes;

import com.amazonaws.services.sns.model.MessageAttributeValue;

public class IdvMaxPriceMessageAttributeValue extends MessageAttributeValue {

    public IdvMaxPriceMessageAttributeValue() {
        withStringValue("0.50");
        withDataType("Number");
    }

}
