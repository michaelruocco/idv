package uk.co.idv.domain.entities.phonenumber;

import java.util.UUID;

public interface PhoneNumber {

    UUID getId();

    String getType();

    String getValue();

    boolean isMobile();

}
