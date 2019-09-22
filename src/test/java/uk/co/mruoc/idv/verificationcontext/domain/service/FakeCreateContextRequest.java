package uk.co.mruoc.idv.verificationcontext.domain.service;

import uk.co.mruoc.idv.domain.model.activity.FakeOnlinePurchase;
import uk.co.mruoc.idv.domain.model.channel.Rsa;
import uk.co.mruoc.idv.identity.domain.model.FakeCreditCardNumber;

public class FakeCreateContextRequest extends CreateContextRequest {

    public FakeCreateContextRequest() {
        super(new Rsa(),
                new FakeOnlinePurchase(),
                new FakeCreditCardNumber()
        );
    }
}
