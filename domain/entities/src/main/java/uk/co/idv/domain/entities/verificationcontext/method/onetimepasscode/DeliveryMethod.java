package uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode;

import java.util.UUID;

public interface DeliveryMethod {

    UUID getId();

    String getType();

    String getValue();

}
