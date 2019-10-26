package uk.co.mruoc.idv.verificationcontext.domain.service;

import uk.co.mruoc.idv.domain.model.activity.FakeOnlinePurchase;
import uk.co.mruoc.idv.domain.model.channel.Rsa;
import uk.co.mruoc.idv.identity.domain.model.AliasesMother;

public class FakeCreateContextRequest extends CreateContextRequest {

    public FakeCreateContextRequest() {
        super(new Rsa(),
                new FakeOnlinePurchase(),
                AliasesMother.creditCardNumber()
        );
    }

}
