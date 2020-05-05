package uk.co.idv.domain.entities.phonenumber;

import lombok.Builder;

import java.util.UUID;

public class OtherPhoneNumber extends AbstractPhoneNumber {

    public static final String TYPE = "other";

    @Builder
    public OtherPhoneNumber(final UUID id, final String value) {
        super(id, TYPE, value);
    }

    @Override
    public boolean isMobile() {
        return false;
    }

}
