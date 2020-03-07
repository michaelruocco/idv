package uk.co.idv.domain.usecases.verification.onetimepasscode;

import uk.co.idv.domain.entities.verification.onetimepasscode.OneTimePasscodeDelivery;

public interface OneTimePasscodeSender {

    void send(final OneTimePasscodeDelivery delivery);

}
