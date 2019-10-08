package uk.co.mruoc.idv.lockout.jsonapi;

import uk.co.mruoc.idv.domain.model.activity.OnlinePurchase;
import uk.co.mruoc.idv.domain.model.channel.Rsa;
import uk.co.mruoc.idv.identity.domain.model.FakeCreditCardNumber;

public class FakeResetLockoutStateAttributes extends ResetLockoutStateAttributes {

    FakeResetLockoutStateAttributes() {
        super(Rsa.ID, OnlinePurchase.NAME, new FakeCreditCardNumber());
    }

}
