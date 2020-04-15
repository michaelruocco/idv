package uk.co.idv.domain.entities.phonenumber;

import lombok.Getter;

@Getter
public class OtherPhoneNumber extends PhoneNumber {

    public OtherPhoneNumber(final String value) {
        super(PhoneNumberType.OTHER, value);
    }

}
