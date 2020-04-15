package uk.co.idv.domain.entities.phonenumber;

import lombok.Getter;

@Getter
public class MobilePhoneNumber extends PhoneNumber {

    public MobilePhoneNumber(final String value) {
        super(PhoneNumberType.MOBILE, value);
    }

}
