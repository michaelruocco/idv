package uk.co.idv.domain.entities.phonenumber;

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
        return new MobilePhoneNumber("+447089111111");
    }

    public static PhoneNumber other() {
        return new OtherPhoneNumber("+441604222222");
    }

}
