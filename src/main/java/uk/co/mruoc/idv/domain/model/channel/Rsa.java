package uk.co.mruoc.idv.domain.model.channel;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class Rsa implements Channel {

    public static final String ID = "RSA";

    @Override
    public String getId() {
        return ID;
    }

}
