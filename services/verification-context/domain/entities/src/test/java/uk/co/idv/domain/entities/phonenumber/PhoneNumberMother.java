package uk.co.idv.domain.entities.phonenumber;


import java.util.Collection;
import java.util.Collections;

public class PhoneNumberMother {

    private PhoneNumberMother() {
        // utility class
    }

    public static Collection<PhoneNumber> oneMobile() {
        return Collections.singleton(mobile());
    }

    public static PhoneNumber mobile() {
        return new MobilePhoneNumber("+447089123456");
    }

    public static PhoneNumber other() {
        return new OtherPhoneNumber("+441604123456");
    }

}
