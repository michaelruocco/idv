package uk.co.idv.onetimepasscode.sender.sns.attributes;

import com.amazonaws.services.sns.model.MessageAttributeValue;

public class IdvSenderIdMessageAttributeValue extends MessageAttributeValue {

    public IdvSenderIdMessageAttributeValue() {
        withStringValue("IDV");
        withDataType("String");
    }

}
