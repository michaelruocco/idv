package uk.co.idv.domain.usecases.verificationcontext;

import uk.co.idv.domain.entities.activity.FakeOnlinePurchase;
import uk.co.idv.domain.entities.channel.ChannelMother;
import uk.co.idv.domain.entities.identity.alias.AliasesMother;

public class FakeCreateContextRequest extends CreateContextRequest {

    public FakeCreateContextRequest() {
        super(ChannelMother.fake(),
                new FakeOnlinePurchase(),
                AliasesMother.creditCardNumber()
        );
    }

}
