package uk.co.idv.domain.entities.phonenumber;

import lombok.Builder;

import java.util.UUID;

public class MobilePhoneNumber extends AbstractPhoneNumber {

    public static final String TYPE = "mobile";

    @Builder
    public MobilePhoneNumber(final UUID id, final String value) {
        super(id, TYPE, value);
    }

    @Override
    public boolean isMobile() {
        return true;
    }

}
