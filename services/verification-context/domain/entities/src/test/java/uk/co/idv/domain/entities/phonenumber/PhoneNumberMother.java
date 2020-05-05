package uk.co.idv.domain.entities.phonenumber;

import java.util.UUID;

public class PhoneNumberMother {

    private PhoneNumberMother() {
        // utility class
    }

    public static PhoneNumbers empty() {
        return new PhoneNumbers();
    }

    public static PhoneNumbers two() {
        return new PhoneNumbers(mobile(), other());
    }

    public static PhoneNumber mobile() {
        return mobile("+447089111111");
    }

    public static PhoneNumber mobile(final String value) {
        return MobilePhoneNumber.builder()
                .id(UUID.fromString("9c3fbf94-d4b7-445e-ae3a-b0935f314c6d"))
                .value(value)
                .build();
    }

    public static PhoneNumber other() {
        return OtherPhoneNumber.builder()
                .id(UUID.fromString("eebdc446-7006-4e09-9d34-03b931448a96"))
                .value("+441604222222")
                .build();
    }

}
