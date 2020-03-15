package uk.co.idv.domain.usecases.verification.onetimepasscode.sender;

import uk.co.idv.domain.entities.verification.onetimepasscode.OneTimePasscodeDelivery;

public interface OneTimePasscodeDeliverySender {

    void send(final OneTimePasscodeDelivery delivery);

}
