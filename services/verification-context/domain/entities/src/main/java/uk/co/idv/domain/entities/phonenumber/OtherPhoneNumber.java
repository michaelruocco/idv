package uk.co.idv.domain.entities.phonenumber;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class OtherPhoneNumber extends PhoneNumber {

    public OtherPhoneNumber(final String value) {
        super(PhoneNumberType.OTHER, value);
    }

}
