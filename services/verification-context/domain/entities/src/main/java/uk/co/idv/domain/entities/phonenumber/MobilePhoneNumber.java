package uk.co.idv.domain.entities.phonenumber;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class MobilePhoneNumber implements PhoneNumber {

    public static final String TYPE = "mobile";

    private final String value;

    @Override
    public String getType() {
        return TYPE;
    }

    public boolean isMobile() {
        return true;
    }

}
