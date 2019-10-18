package uk.co.mruoc.idv.lockout.jsonapi;

import uk.co.mruoc.idv.domain.model.activity.OnlinePurchase;
import uk.co.mruoc.idv.domain.model.channel.Rsa;
import uk.co.mruoc.idv.identity.domain.model.FakeCreditCardNumber;
import uk.co.mruoc.idv.lockout.domain.service.DefaultLockoutRequest;

public class FakeLockoutRequest extends DefaultLockoutRequest {

    FakeLockoutRequest() {
        super(Rsa.ID, OnlinePurchase.NAME, new FakeCreditCardNumber());
    }

}
