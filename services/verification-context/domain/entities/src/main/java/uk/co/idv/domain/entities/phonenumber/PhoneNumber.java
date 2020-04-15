package uk.co.idv.domain.entities.phonenumber;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class PhoneNumber {

    private final PhoneNumberType type;
    private final String value;

    public boolean isMobile() {
        return PhoneNumberType.MOBILE.equals(type);
    }

}
