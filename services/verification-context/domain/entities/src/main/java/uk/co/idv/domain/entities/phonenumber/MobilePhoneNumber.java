package uk.co.idv.domain.entities.phonenumber;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MobilePhoneNumber extends PhoneNumber {

    public MobilePhoneNumber(final String value) {
        super(PhoneNumberType.MOBILE, value);
    }

}
