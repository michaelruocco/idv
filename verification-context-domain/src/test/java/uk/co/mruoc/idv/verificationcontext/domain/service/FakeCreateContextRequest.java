package uk.co.mruoc.idv.verificationcontext.domain.service;

import uk.co.idv.domain.entities.activity.FakeOnlinePurchase;
import uk.co.idv.domain.entities.channel.Rsa;
import uk.co.idv.domain.entities.identity.AliasesMother;

public class FakeCreateContextRequest extends CreateContextRequest {

    public FakeCreateContextRequest() {
        super(new Rsa(),
                new FakeOnlinePurchase(),
                AliasesMother.creditCardNumber()
        );
    }

}
