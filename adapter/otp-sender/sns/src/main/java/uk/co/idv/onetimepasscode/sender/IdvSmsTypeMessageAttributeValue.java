package uk.co.idv.onetimepasscode.sender;

import com.amazonaws.services.sns.model.MessageAttributeValue;

public class IdvSmsTypeMessageAttributeValue extends MessageAttributeValue {

    public IdvSmsTypeMessageAttributeValue() {
        withStringValue("Transactional");
        withDataType("String");
    }

}
