package uk.co.idv.domain.entities.channel;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class Rsa implements Channel {

    public static final String ID = "RSA";

    @Override
    public String getId() {
        return ID;
    }

}
