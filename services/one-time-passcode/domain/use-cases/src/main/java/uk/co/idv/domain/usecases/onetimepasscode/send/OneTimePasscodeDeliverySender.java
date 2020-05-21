package uk.co.idv.domain.usecases.onetimepasscode.send;

import uk.co.idv.domain.entities.onetimepasscode.OneTimePasscodeDelivery;

public interface OneTimePasscodeDeliverySender {

    String send(final OneTimePasscodeDelivery delivery);

}
