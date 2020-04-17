package uk.co.idv.json.phonenumber;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.domain.entities.phonenumber.PhoneNumber;
import uk.co.idv.domain.entities.phonenumber.PhoneNumbers;

public class PhoneNumberModule extends SimpleModule {

    public PhoneNumberModule() {
        super("phone-number-module", Version.unknownVersion());

        setMixInAnnotation(PhoneNumber.class, PhoneNumberMixin.class);
        setMixInAnnotation(PhoneNumbers.class, PhoneNumbersMixin.class);

        addDeserializer(PhoneNumber.class, new PhoneNumberDeserializer());
        addDeserializer(PhoneNumbers.class, new PhoneNumbersDeserializer());
    }

}
