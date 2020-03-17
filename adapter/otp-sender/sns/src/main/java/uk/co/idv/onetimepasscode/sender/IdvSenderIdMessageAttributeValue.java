package uk.co.idv.onetimepasscode.sender;

import com.amazonaws.services.sns.model.MessageAttributeValue;

public class IdvSenderIdMessageAttributeValue extends MessageAttributeValue {

    public IdvSenderIdMessageAttributeValue() {
        withStringValue("IDV");
        withDataType("String");
    }

}
