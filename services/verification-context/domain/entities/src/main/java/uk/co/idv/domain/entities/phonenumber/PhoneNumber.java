package uk.co.idv.domain.entities.phonenumber;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class PhoneNumber {

    private final PhoneNumberType type;
    private final String value;

    public boolean isMobile() {
        return PhoneNumberType.MOBILE.equals(type);
    }

}
