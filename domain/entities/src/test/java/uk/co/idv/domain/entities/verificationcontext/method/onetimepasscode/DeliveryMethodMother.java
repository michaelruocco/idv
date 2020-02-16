package uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

public class DeliveryMethodMother {

    private DeliveryMethodMother() {
        // utility class
    }

    public static Collection<DeliveryMethod> oneSms() {
        return Collections.singleton(sms());
    }

    public static SmsDeliveryMethod sms() {
        return new SmsDeliveryMethod(
                UUID.fromString("2a82fcb5-19d4-469d-9c1b-4b2318c1e3f4"),
                "07819389980"
        );
    }

}
